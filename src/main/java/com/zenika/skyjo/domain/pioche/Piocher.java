package com.zenika.skyjo.domain.pioche;

import com.zenika.skyjo.domain.common.Carte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partie")
public class Piocher {

    @Autowired
    private Pioche pioche;

    @GetMapping(path = "/{partieId}/piocher")
    @ResponseStatus(HttpStatus.OK)
    public Carte pioche(@PathVariable int partieId,
                        @RequestHeader String joueur){
        return pioche.tirerProchaineCarte();
    }
}
