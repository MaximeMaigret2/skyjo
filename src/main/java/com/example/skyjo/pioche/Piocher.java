package com.example.skyjo.pioche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skyjo/pioche")
public class Piocher {

    @Autowired
    private Pile pile;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Carte pioche(){
        return pile.tirerProchaineCarte();
    }
}
