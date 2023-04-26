package com.zenika.skyjo.domain.joue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JouerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void je_peux_remplacer_une_carte() throws Exception {

        String positionDeLaCarteARemplacer = """
                {
                    "colonne": 3,
                    "ligne": 2,
                    "jou
                }
                """;

        mockMvc.perform(put("/partie/1/jouer/remplacer")
                .content(positionDeLaCarteARemplacer)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}