package com.zenika.skyjo.joue;

import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MancheBuilderTest;
import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Valeur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@IntegrationTest
class JouerControleurIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MancheRepository mancheRepository;

    @Test
    void je_peux_remplacer_une_carte() {

        Manche manche = MancheBuilderTest.nouvelleMancheDeTest("1")
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
                .header("joueur", "Awa")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(positionDeLaCarteARemplacer)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Manche.class)
                .getResponseBody().blockFirst();
    }

}