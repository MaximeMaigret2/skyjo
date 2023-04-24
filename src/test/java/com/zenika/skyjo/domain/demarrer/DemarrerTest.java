package com.zenika.skyjo.domain.demarrer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemarrerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void je_veux_demarrer_une_nouvelle_partie()throws Exception{

        String joueurs = """
                [
                    "Maxime",
                    "Awa"
                ]
                """;

        mockMvc.perform(post("/partie/demarrerUneNouvellePartie")
                .content(joueurs)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1));
    }

}