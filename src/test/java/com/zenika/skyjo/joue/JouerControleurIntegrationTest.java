package com.zenika.skyjo.joue;

import com.zenika.skyjo.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@IntegrationTest
class JouerControleurIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void je_peux_remplacer_une_carte() throws Exception {
        // FIXME
//        String positionDeLaCarteARemplacer = """
//                {
//                    "colonne": 3,
//                    "ligne": 2
//                }
//                """;
//
//        mockMvc.perform(put("/partie/1/jouer/remplacer")
//                        .header("joueur", "Awa")
//                        .content(positionDeLaCarteARemplacer)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
    }

}