package com.zenika.skyjo.joue;

import com.zenika.skyjo.common.AffichageManche;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MancheTestBuilder;
import com.zenika.skyjo.domain.*;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;
import static org.hamcrest.Matchers.*;

@IntegrationTest
class JouerControleurIntegrationTest {

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
                .expectBody()
                .jsonPath("$.plateaux[0:].joueur").value(containsInAnyOrder("Awa"))
                .jsonPath("$.plateaux[0:].cartes[*]..statut").value(hasSize(12))
                .jsonPath("$.plateaux[0].cartes[2][3].valeur").value(is(Valeur.DEUX.toString()))
                .jsonPath("$.plateaux[0].cartes[2][3].statut").value(is(Statut.VISIBLE.toString()))
                .jsonPath("$.defausse.cartes[0].statut").value(is(Statut.VISIBLE.toString()))
                .jsonPath("$.defausse.cartes[0].valeur").value(is(Valeur.ZERO.toString()))
                .consumeWith(AffichageManche.convertirEtAfficher());
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