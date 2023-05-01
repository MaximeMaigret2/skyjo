package com.zenika.skyjo.pioche;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PiocheIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void je_peux_piocher_une_carte() throws Exception {
		// FIXME
//		Pioche.construireLaPioche();
//
//		mockMvc.perform(get("/partie/1/piocher")
//						.header("joueur", "Awa"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("valeur").value("MOINS_DEUX"));
	}

}
