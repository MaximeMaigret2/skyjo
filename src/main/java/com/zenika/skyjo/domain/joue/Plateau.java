package com.zenika.skyjo.domain.joue;

import com.zenika.skyjo.domain.common.Carte;
import com.zenika.skyjo.domain.pioche.Pioche;

public class Plateau {

    private final Carte[][] cartes;


    private Carte carteEnMain;

    private final String joueur;

    public static Plateau creerPlateauPour(String joueur, Pioche pioche) {
        return new Plateau(joueur, pioche);
    }

    private Plateau(String joueur, Pioche pioche) {
        this.joueur = joueur;
        cartes = initialisation(pioche);
    }

    private Carte[][] initialisation(Pioche pioche) {
        return new Carte[0][];
    }

    public Carte[][] getCartes() {
        return cartes;
    }

    public void setCarteEnMain(Carte carteEnMain) {
        this.carteEnMain = carteEnMain;
    }
}
