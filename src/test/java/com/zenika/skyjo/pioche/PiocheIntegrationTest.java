package com.zenika.skyjo.pioche;

import com.zenika.skyjo.common.IntegrationTest;
import com.zenika.skyjo.common.MancheBuilderTest;
import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Valeur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

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
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.plateaux[0].joueur").isEqualTo("Awa")
				.jsonPath("$.plateaux[0].carteEnMain.valeur").isEqualTo("DEUX")
				.jsonPath("$.plateaux[0].carteEnMain.statut").isEqualTo("VISIBLE");
	}

}
