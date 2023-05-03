package com.zenika.skyjo.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenarioTestUtils {

    @Autowired
    private WebTestClient webTestClient;

    @NotNull
    public WebTestClient.ResponseSpec initialiseUnePartieAvecDesJoueurs(List<String> joueurs) {

        String jsonJoueurs = "{\"joueurs\":[" + joueurs.stream()
                .collect(Collectors.joining("\", \"", "\"", "\"")) + "]}";

        return webTestClient.post()
                .uri("/manches/nouvellePartie")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonJoueurs)
                .exchange();
    }

}

