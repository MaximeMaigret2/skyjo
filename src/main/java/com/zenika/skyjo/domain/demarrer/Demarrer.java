package com.zenika.skyjo.domain.demarrer;

import com.zenika.skyjo.domain.joue.Plateau;
import com.zenika.skyjo.domain.pioche.Pioche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partie")
public class Demarrer {

    @PostMapping("demarrerUneNouvellePartie")
    @ResponseStatus(HttpStatus.CREATED)
    public int demarrerUneNouvellePartie(@RequestBody List<String> joueurs){

        int idPartie = 1; // TODO verifier dans la base le nombre de partie

        Pioche pioche = Pioche.creerPioche();
        for (String joueur : joueurs) {
            Plateau plateau = Plateau.creerPlateauPour(joueur, pioche);
            plateau.getCartes(); // TODO : sauvegarder en base le plateau pour le joueur donné et la partie donnée
        }

        return idPartie;
    }
}
