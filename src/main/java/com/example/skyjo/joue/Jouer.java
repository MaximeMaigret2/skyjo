package com.example.skyjo.joue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/skyjo/jouer")
public class Jouer {

    @PutMapping("/remplacer")
    @ResponseStatus(HttpStatus.OK)
    public Plateau remplacer(@RequestBody Position position){
        throw  new UnsupportedOperationException();
    }
}
