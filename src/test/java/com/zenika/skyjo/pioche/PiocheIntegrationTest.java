package com.zenika.skyjo.pioche;

import com.zenika.skyjo.common.AffichageManche;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MancheTestBuilder;
import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Valeur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;

@IntegrationTest
class PiocheIntegrationTest {
    // FiXME add Defausse pioche et cas non passant

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MancheRepository mancheRepository;

    @Test
    void je_peux_piocher_une_carte() {

        Manche manche = MancheTestBuilder.nouvelleMancheDeTest("1")
                .pourJoueur("Awa")
                .avecPiocheFixee(List.of(Carte.uneCarteDe(Valeur.DEUX)))
                .avecDefausseFixee(Carte.uneCarteDe(Valeur.TROIS))
                .avecPlateauQuelconqueCache()
                .build();

        mancheRepository.save(manche);

        webTestClient.post()
                .uri("/manches/1/piocher/pile")
                .header(JOUEUR, "Awa")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.plateaux[0].joueur").isEqualTo("Awa")
                .jsonPath("$.plateaux[0].carteEnMain.valeur").isEqualTo("DEUX")
                .jsonPath("$.plateaux[0].carteEnMain.statut").isEqualTo("VISIBLE")
                .consumeWith(AffichageManche.convertirEtAfficher());
    }

}
