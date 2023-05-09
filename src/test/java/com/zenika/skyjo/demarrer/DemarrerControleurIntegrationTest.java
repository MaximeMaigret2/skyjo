package com.zenika.skyjo.demarrer;

import com.zenika.skyjo.common.AffichageManche;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MongoDbCommand;
import com.zenika.skyjo.domain.Etat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

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
                .jsonPath("$.etat").isEqualTo(Etat.INITIALISATION.toString())
                .consumeWith(AffichageManche.convertirEtAfficher());
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

    @Test
    @MongoDbCommand(commands = {"mongodb/1-manches-init.json", "mongodb/2-manches-init-en-cours.json"})
    void je_veux_mal_engager_un_joueur_dans_une_nouvelle_partie() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Et que je décide d'engager Maxime avec deux cartes comme ci-dessus
        // +-----------+-----------+-----------+-----------+
        // |     [ 2 ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] |    [ -2 ] |
        // +-----------+-----------+-----------+-----------+
        // On a les positions des deux cartes suivantes qui ne sont pas correctes :
        String jsonPositions = """
                {
                   "positions":[
                      {
                         "ligne":4,
                         "colonne":3
                      },
                      {
                         "colonne":0,
                         "ligne":3
                      }
                   ]
                }
                """;

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Maxime sur la bonne partie
                        .path("/manches/{id}/engager")
                        .build("64568aedb0ca4f690484c22c"))
                .header(JOUEUR, "Maxime")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPositions)
                .exchange()
                // alors c'est une mauvaise demande d'engagement
                .expectStatus().isBadRequest();
    }


    @Test
    @MongoDbCommand("mongodb/1-manches-init.json")
    void je_veux_engager_un_joueur_dans_une_nouvelle_partie() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Et que je décide d'engager Maxime avec deux cartes comme ci-dessus
        // +-----------+-----------+-----------+-----------+
        // |     [ 2 ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] |    [ -2 ] |
        // +-----------+-----------+-----------+-----------+
        // On a les positions des deux cartes suivantes :
        String jsonPositions = """
                {
                   "positions":[
                      {
                         "ligne":2,
                         "colonne":3
                      },
                      {
                         "colonne":0,
                         "ligne":0
                      }
                   ]
                }
                """;

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Maxime sur la bonne partie
                        .path("/manches/{id}/engager")
                        .build("64568aedb0ca4f690484c22c"))
                .header(JOUEUR, "Maxime")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPositions)
                .exchange()
                // alors c'est OK
                .expectStatus().isOk()
                .expectBody()
                // les deux joueurs sont bien Awa et Maxime
                .jsonPath("$.plateaux[0:].joueur").value(containsInAnyOrder("Awa", "Maxime"))
                // le plateau de Maxime a deux cartes retournées
                .jsonPath("$.plateaux[1].cartes[*][?(@.statut == 'VISIBLE')]").value(hasSize(2))
                // et il y a deux valeurs de présentes également
                .jsonPath("$.plateaux[1].cartes[*]..valeur").value(hasSize(2))
                // les autres sont toujours faces cachées
                .jsonPath("$.plateaux[1].cartes[*][?(@.statut == 'CACHE')]").value(hasSize(10))
                // et un état INITIALISATION
                .jsonPath("$.etat").isEqualTo(Etat.INITIALISATION.toString())
                .consumeWith(AffichageManche.convertirEtAfficher());
    }


    @Test
    @MongoDbCommand({"mongodb/1-manches-init.json", "mongodb/2-manches-init-en-cours.json"})
    void je_veux_engager_deux_joueurs_dans_une_partie_qui_deviendra_en_cours() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Et que je décide d'engager Julien et Laurène avec deux cartes comme ci-dessus
        //
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] |     [ 1 ] |     [ 0 ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        //
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] |    [ -1 ] |     [ 5 ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        // | [ skyjo ] | [ skyjo ] | [ skyjo ] | [ skyjo ] |
        // +-----------+-----------+-----------+-----------+
        //
        // On a les positions des deux cartes suivantes :
        String jsonPositions = """
                {
                   "positions":[
                      {
                         "ligne":1,
                         "colonne":1
                      },
                      {
                         "colonne":2,
                         "ligne":1
                      }
                   ]
                }
                """;
        // Quand
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Julien sur la bonne partie
                        .path("/manches/{id}/engager")
                        .build("6457703203dd1778669a0410"))
                .header(JOUEUR, "Julien")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPositions)
                .exchange()
                // alors c'est OK
                .expectStatus().isOk()
                .expectBody().consumeWith(AffichageManche.convertirEtAfficher());
        // Et
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Laurène sur la bonne partie
                        .path("/manches/{id}/engager")
                        .build("6457703203dd1778669a0410"))
                .header(JOUEUR, "Laurène")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPositions)
                .exchange()
                // alors c'est OK
                .expectStatus().isOk()
                .expectBody()
                // les deux joueurs sont bien Awa et Maxime
                .jsonPath("$.plateaux[0:].joueur").value(containsInAnyOrder("Julien", "Laurène"))
                // chaque plateau dispose de deux cartes retournées 2+2
                .jsonPath("$.plateaux[0:].cartes[*][?(@.statut == 'VISIBLE')]").value(hasSize(4))
                // et il y a deux valeurs de présentes également pour chaque 2+2
                .jsonPath("$.plateaux[0:].cartes[*]..valeur").value(hasSize(4))
                // les autres sont toujours faces cachées
                .jsonPath("$.plateaux[0:].cartes[*][?(@.statut == 'CACHE')]").value(hasSize(20))
                // et un état ENCOURS
                .jsonPath("$.etat").isEqualTo(Etat.ENCOURS.toString())
                .consumeWith(AffichageManche.convertirEtAfficher());
    }


}