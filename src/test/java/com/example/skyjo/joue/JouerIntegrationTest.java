package com.example.skyjo.joue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                    "ligne": 2
                }
                """;

        mockMvc.perform(post("/skyjo/remplacer")
                .content(positionDeLaCarteARemplacer))
                .andDo(print())
                .andExpect(status().isOk());
    }

}