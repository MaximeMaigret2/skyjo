package com.zenika.skyjo.domain.pioche;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PiocheIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void je_peux_piocher_une_carte() throws Exception {
		Pioche.creerPioche();

		mockMvc.perform(get("/partie/1/piocher")
						.header("joueur", "Awa"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("valeur").value("MOINS_DEUX"));
	}

}
