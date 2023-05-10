package com.zenika.skyjo.joue;

import com.zenika.skyjo.common.AffichageManche;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MancheTestBuilder;
import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Valeur;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;

@IntegrationTest
class JouerControleurIntegrationTest {
    // FiXME add defausserCarteEnMainRevelerCartePlateau pioche et cas non passant

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MancheRepository mancheRepository;

    @Test
    void je_peux_remplacer_une_carte() {

        Manche manche = MancheTestBuilder.nouvelleMancheDeTest("1")
                .pourJoueur("Awa")
                .avecPlateauQuelconqueCache()
                .avecCarteEnMain(Carte.uneCarteDe(Valeur.DEUX))
                .avecDefausseFixee(Carte.uneCarteDe(Valeur.UN))
                .build();

        mancheRepository.save(manche);

        String positionDeLaCarteARemplacer = """
                {
                    "colonne": 3,
                    "ligne": 2
                }
                """;

        webTestClient.post()
                .uri("/manches/1/jouer/remplacer")
                .header(JOUEUR, "Awa")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(positionDeLaCarteARemplacer)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MancheDto.class)
                .consumeWith(mancheDto -> AffichageManche.afficherManche(mancheDto.getResponseBody()));
    }

    @Test
    void je_ne_peux_pas_remplacer_une_carte_si_aucune_carte_main() {

        Manche manche = MancheTestBuilder.nouvelleMancheDeTest("1")
                .pourJoueur("Awa")
                .avecPlateauQuelconqueCache()
                .avecCarteEnMain(null)
                .avecDefausseFixee(Carte.uneCarteDe(Valeur.UN))
                .build();

        mancheRepository.save(manche);

        String positionDeLaCarteARemplacer = """
                {
                    "colonne": 3,
                    "ligne": 2
                }
                """;

        webTestClient.post()
                .uri("/manches/1/jouer/remplacer")
                .header(JOUEUR, "Awa")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(positionDeLaCarteARemplacer)
                .exchange()
                .expectStatus().isBadRequest();
    }

}