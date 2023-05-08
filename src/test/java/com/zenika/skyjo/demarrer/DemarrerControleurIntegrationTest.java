package com.zenika.skyjo.demarrer;

import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MongoDbCommand;
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
    void je_veux_demarrer_une_nouvelle_partie() {
        // Étant donné qu'on souhaite créer une manche de trois joueurs
        String jsonJoueurs = """
                {
                  "joueurs": [
                    "Amélie",
                    "Mickaël",
                    "Yoan"
                  ]
                }
                """;

        // Quand
        webTestClient.post()
                // je demande la création d'une nouvelle partie
                .uri("/manches/nouvellePartie")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonJoueurs)
                .exchange()
                // Alors elle est créée
                .expectStatus().isCreated()
                .expectBody()
                // On a trois joueurs
                .jsonPath("$.plateaux.length()").isEqualTo(3)
                // et donc une pioche de 113 cartes
                .jsonPath("$.pioche.cartes.length()").isEqualTo(113)
                // une défausse de 1 carte
                .jsonPath("$.defausse.cartes.length()").isEqualTo(1)
                // et un état INITIALISATION
                .jsonPath("$.etat").isEqualTo(Etat.INITIALISATION.toString());
    }


    @Test
    void je_veux_demarrer_une_nouvelle_partie_avec_un_seul_joueur() {
        // Étant donné qu'on souhaite créer une manche de un seul joueur
        String jsonJoueurs = """
                {
                  "joueurs": [
                    "Amélie"
                  ]
                }
                """;

        // Quand
        webTestClient.post()
                // je demande la création d'une nouvelle partie
                .uri("/manches/nouvellePartie")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonJoueurs)
                .exchange()
                // Alors je ne peux pas car le nombre minimum de joueur est de deux
                .expectStatus().isBadRequest();
    }

//    @Test
//    @MongoDbCommand(commands = {"mongodb/1-manche-init.json", "mongodb/2-manches-init.json"})
//    void je_veux_engager_un_joueur_dans_une_nouvelle_partie() {
//        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
//        // Quand
//    }

    @Test
    @MongoDbCommand(commands = {"mongodb/1-manche-init.json", "mongodb/2-manches-init.json"})
    void je_veux_mal_engager_un_joueur_dans_une_nouvelle_partie() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Et que je décide d'engager Maxime avec deux cartes comme ci-dessus
        //  +---+---+---+---+
        //  | V | C | C | C |
        //  +---+---+---+---+
        //  | C | C | C | V |
        //  +---+---+---+---+
        //  | C | C | C | C |
        //  +---+---+---+---+
        String jsonPositions = """
                {
                   "positions":[
                      {
                         "ligne":2,
                         "colonne":3
                      },
                      {
                         "colonne":0
                         "ligne":0,
                      }
                   ]
                }
                """;

//        webTestClient.post()
//                // je soumet les positions
//                .uri("/manches/nouvellePartie")
//                .header(JOUEUR, )
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(jsonPositions)

    }




    @Test
    @MongoDbCommand("mongodb/1-manche-init.json")
    void je_veux_engager_un_joueur_dans_une_nouvelle_partie() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Quand
    }

}