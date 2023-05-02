package com.zenika.skyjo.demarrer;

import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.domain.Etat;
import com.zenika.skyjo.utils.ScenarioTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@IntegrationTest
class DemarrerControleurIntegrationTest {

    @Autowired
    private ScenarioTestUtils scenarioTestUtils;

    @Test
    void je_veux_demarrer_une_nouvelle_partie() throws Exception {
        // Given 2 joueurs
        // When
        scenarioTestUtils.initialiseUnePartieAvecDesJoueurs(List.of("Awa", "Maxime"))
                // Then
                .expectStatus().isCreated()
                .expectBody()
                // On a deux joueurs
                .jsonPath("$.plateaux.length()").isEqualTo(2)
                // et donc une pioche de 125 cartes
                .jsonPath("$.pioche.cartes.length()").isEqualTo(125)
                // une défausse de 1 carte
                .jsonPath("$.defausse.cartes.length()").isEqualTo(1)
                // et un état INITIALISATION
                .jsonPath("$.etat").isEqualTo(Etat.INITIALISATION.toString());
    }

    @Test
    void je_veux_demarrer_engager_un_joueur_dans_une_nouvelle_partie() throws Exception {
        // FIXME
    }

}