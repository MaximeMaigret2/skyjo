package com.zenika.skyjo.consulter;

import com.zenika.skyjo.common.AffichageManche;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MongoDbCommand;
import com.zenika.skyjo.domain.Statut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@IntegrationTest
public class ConsulterControleurIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @MongoDbCommand(commands = {"mongodb/1-manches-init.json", "mongodb/2-manches-init-en-cours.json"})
    public void acceder_a_une_manche_etat_initialisation_et_le_contenu_est_cache() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Quand
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        // je recupére la manche avec Julien et Laurène
                        .path("/manches/{id}")
                        .build("6457703203dd1778669a0410"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // alors elle existe
                .expectStatus().isOk()
                .expectBody()
                // les deux joueurs sont bien Julien et Laurène
                .jsonPath("$.plateaux[0:].joueur").value(containsInAnyOrder("Julien", "Laurène"))
                // le statut des cartes de leurs plateaux est CACHE et on dispose de 24 cartes -> 12 + 12
                .jsonPath("$.plateaux[0:].cartes[*]..statut").value(both(hasSize(24)).and(everyItem(is(Statut.CACHE.toString()))))
                // et ils n'ont pas les valeurs des cartes
                .jsonPath("$.plateaux[0:].cartes[*]..valeur").doesNotExist()
                // le statut des cartes de la pioche est CACHE et on dispose d'une pioche de 125 cartes
                .jsonPath("$.pioche.cartes[*].statut").value(both(hasSize(125)).and(everyItem(is(Statut.CACHE.toString()))))
                // et ils n'ont pas les valeurs des cartes de la pioche
                .jsonPath("$.pioche.cartes[*].valeur").doesNotExist()
                // le statut des cartes de la défausse est VISIBLE et on dispose d'une défausse de 1 carte
                .jsonPath("$.defausse.cartes[*].statut").value(both(hasSize(1)).and(contains(Statut.VISIBLE.toString())))
                // et ils disposent de la valeur de la carte de la défausse
                .jsonPath("$.defausse.cartes[*].valeur").exists()
                .consumeWith(AffichageManche.convertirEtAfficher());
    }

    @Test
    @MongoDbCommand(commands = {"mongodb/1-manches-init.json", "mongodb/2-manches-init-en-cours.json"})
    public void la_manche_n_existe_pas() {
        // Étant donné qu'on a des manches en base de données cf. MongoDbCommand
        // Quand
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        // je recupere une manche avec un identifiant aléatoire
                        .path("/manches/{id}")
                        .build(UUID.randomUUID()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // alors elle n'existe pas
                .expectStatus().isNotFound();
    }
}
