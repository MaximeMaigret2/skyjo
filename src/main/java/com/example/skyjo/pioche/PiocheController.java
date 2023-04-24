package com.example.skyjo.pioche;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.example.skyjo.pioche.Carte.uneCarteDe;
import static com.example.skyjo.pioche.Valeur.valeur;

@RestController
@RequestMapping("/skyjo/pioche")
public class PiocheController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Carte pioche(){
        return uneCarteDe(valeur(12)); // mock
    }
}
