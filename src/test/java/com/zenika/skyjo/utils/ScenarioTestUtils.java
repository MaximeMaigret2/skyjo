package com.zenika.skyjo.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScenarioTestUtils {
    @Autowired
    private WebTestClient webTestClient;


    @NotNull
    public WebTestClient.ResponseSpec initialiseUnePartieAvecDesJoueurs(List<String> joueurs) {

        String jsonJoueurs = "{\"joueurs\":[" + joueurs.stream()
                .collect(Collectors.joining("\", \"", "\"", "\"")) + "]}";

        return webTestClient.post()
                .uri("/manches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonJoueurs)
                .exchange();
    }

}

