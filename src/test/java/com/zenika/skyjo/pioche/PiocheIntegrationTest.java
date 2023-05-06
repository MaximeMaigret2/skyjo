package com.zenika.skyjo.pioche;

import com.mongodb.DBObject;
import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MancheBuilderTest;
import com.zenika.skyjo.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import com.mongodb.util.JSON;
import com.mongodb.BasicDBObject;

import java.util.LinkedList;
import java.util.List;

@IntegrationTest
class PiocheIntegrationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private MancheRepository mancheRepository;

	@Test
	void je_peux_piocher_une_carte() {

		Manche manche = MancheBuilderTest.nouvelleMancheDeTest("1")
						.pourJoueur("Awa")
						.avecPiocheFixee(List.of(Carte.uneCarteDe(Valeur.DEUX)))
						.avecPlateauQuelconque()
						.build();

		mancheRepository.save(manche);

		webTestClient.post()
				.uri("/manches/1/piocher/pile")
				.header("joueur", "Awa")
				.exchange()
				.expectStatus().isOk();
	}

}
