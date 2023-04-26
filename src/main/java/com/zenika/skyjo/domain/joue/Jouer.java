package com.zenika.skyjo.domain.joue;

import com.zenika.skyjo.domain.common.Carte;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partie")
public class Jouer {

    @PutMapping("/{id}/jouer/remplacer")
    @ResponseStatus(HttpStatus.OK)
    public Carte[][] remplacer(@PathVariable int partie,
                               @RequestBody Position coup,
                               @RequestHeader String joueur){
        throw  new UnsupportedOperationException();
    }
}
