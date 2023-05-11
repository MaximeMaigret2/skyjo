package com.zenika.skyjo.scenario;

import com.zenika.skyjo.common.AffichageManche;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;

@IntegrationTest
class SkyjoEndToEndTest {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    class Manche {

        @Test
        void je_fais_un_tour_de_jeu() {
            String awa = "Awa";
            String maxime = "Maxime";

            String id = jeDemarreLaPartiePour(awa, maxime);

            jePiocheUneCarteSurLaPile(id, awa);

            jeRemplaceUneCarteDeMonPlateauPour(id, awa);

            jePiocheUneCarteSurLaDefausse(id, maxime);

            jeRemplaceUneCarteDeMonPlateauPour(id, maxime);
        }

    }

    private void jeRemplaceUneCarteDeMonPlateauPour(String id, String joueur) {
        String positionDeLaCarteARemplacer = """
                {
                    "colonne": 3,
                    "ligne": 2
                }
                """;

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Maxime sur la bonne partie
                        .path("/manches/{id}/jouer/remplacer")
                        .build(id))
                .header(JOUEUR, joueur)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(positionDeLaCarteARemplacer)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MancheDto.class)
                .consumeWith(mancheDto -> AffichageManche.afficherManche(mancheDto.getResponseBody()));
    }

    private String jeDemarreLaPartiePour(String... joueurs) {
        String contentJoueurs = construireJsonAPartirDe(joueurs);

        MancheDto resultat = webTestClient.post()
                .uri("/manches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(contentJoueurs)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MancheDto.class)
                .consumeWith(mancheDto -> AffichageManche.afficherManche(mancheDto.getResponseBody()))
                .returnResult().getResponseBody();

        return resultat.id();
    }

    @NotNull
    private String construireJsonAPartirDe(String[] joueurs) {
        String listeJoueurs = Stream.of(joueurs)
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(",", "[", "]"));
        String debut = "{ \"joueurs\": ";
        String fin = " }";
        return debut + listeJoueurs + fin;
    }


    private void jePiocheUneCarteSurLaPile(String id, String joueur) {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Maxime sur la bonne partie
                        .path("/manches/{id}/piocher/pile")
                        .build(id))
                .header(JOUEUR, joueur)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(AffichageManche.convertirEtAfficher());
    }


    private void jePiocheUneCarteSurLaDefausse(String id, String joueur) {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        // j'engage Maxime sur la bonne partie
                        .path("/manches/{id}/piocher/defausse")
                        .build(id))
                .header(JOUEUR, joueur)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(AffichageManche.convertirEtAfficher());
    }


}
