package com.zenika.skyjo.demarrer;

import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.domain.Etat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@IntegrationTest
class DemarrerControleurIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void je_veux_demarrer_une_nouvelle_partie() throws Exception {
        // Given
        String joueurs = """
                {
                  "joueurs": ["Maxime", "Awa"]
                }
                """;
        // When
        webTestClient.post()
                .uri("/manches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(joueurs)
                .exchange()
                // Then
                .expectStatus().isCreated()
                .expectBody()
                // On a deux joueurs
                .jsonPath("$.plateaux.length()").isEqualTo(2)
                // et donc une pioche de 125 cartes
                .jsonPath("$.pioche.cartes.length()").isEqualTo(125)
                // une defausse de 1 carte
                .jsonPath("$.defausse.cartes.length()").isEqualTo(1)
                // et un etat INITIALISATION
                .jsonPath("$.etat").isEqualTo(Etat.INITIALISATION.toString());
    }

}