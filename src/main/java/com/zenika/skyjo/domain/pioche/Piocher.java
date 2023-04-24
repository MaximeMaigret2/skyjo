package com.zenika.skyjo.domain.pioche;

import com.zenika.skyjo.domain.common.Carte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partie")
public class Piocher {

    @GetMapping(path = "/{partieId}/piocher")
    @ResponseStatus(HttpStatus.OK)
    public Carte pioche(@PathVariable int partieId,
                        @RequestHeader String joueur){
        // Pioche pioche = BDD.recupererPartie(partieId, joueur);
        Pioche pioche = Pioche.creerPioche(); // TODO : recupere la pioche depuis la BDD

        try{
            return pioche.tirerProchaineCarte();
        }catch (PiocheVideException e){
            // TODO : melanger defausse dans pioche
            return null;
        }

    }
}
