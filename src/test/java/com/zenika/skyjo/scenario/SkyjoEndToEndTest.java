package com.zenika.skyjo.scenario;

import com.zenika.skyjo.common.IntegrationTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class SkyjoEndToEndTest {
    // FIXME

    @Autowired
    private WebTestClient webTestClient;

//    @Nested
//    class TourDeJeu {
//
//        @Test
//        void je_fais_un_tour_de_jeu() throws Exception {
//            jeDemarreLaPartie();
//            jePioche();
//            jeRemplaceUneCarteDeMonPlateau();
//        }
//
//        private void jeRemplaceUneCarteDeMonPlateau() throws Exception {
//            jeRemplaceUneCarteDeMonPlateauPour("Awa");
//        }
//
//        private void jePioche() throws Exception {
//            jePiochePour("Awa");
//        }
//
//        private void jeDemarreLaPartie() throws Exception {
//            jeDemarreLaPartiePour("Awa");
//        }
//
//    }
//
//    @Nested
//    class Manche{
//        @Test
//        void je_fais_un_tour_de_jeu() throws Exception {
//            String awa = "Awa";
//            String maxime = "Maxime";
//
//            jeDemarreLaPartiePour(awa, maxime);
//
//            jePiochePour(awa);
//            jeRemplaceUneCarteDeMonPlateauPour(awa);
//
//            jePiochePour(maxime);
//            jeRemplaceUneCarteDeMonPlateauPour(maxime);
//        }
//    }
//
//    private void jeRemplaceUneCarteDeMonPlateauPour(String joueur) throws Exception {
//        String positionDeLaCarteARemplacer = """
//                {
//                    "colonne": 3,
//                    "ligne": 2
//                }
//                """;
//
//        mockMvc.perform(put("/partie/1/jouer/remplacer")
//                        .header("joueur", joueur)
//                        .content(positionDeLaCarteARemplacer)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    private void jePiochePour(String joueur) throws Exception {
//        mockMvc.perform(get("/partie/1/piocher")
//                        .header("joueur", joueur))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("valeur").value("MOINS_DEUX"));
//    }
//
//    private void jeDemarreLaPartiePour(String ... joueurs) throws Exception {
//        String contentJoueurs = construireJsonAPartirDe(joueurs);
//
//        mockMvc.perform(post("/partie/demarrerUneNouvellePartie")
//                        .content(contentJoueurs)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").value(1));
//    }
//
//    @NotNull
//    private String construireJsonAPartirDe(String[] joueurs) {
//        return Stream.of(joueurs)
//                .map(s-> "\""+s+"\"")
//                .collect(Collectors.joining(",","[","]"));
//    }

}
