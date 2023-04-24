package com.zenika.skyjo.domain.joue;

import com.zenika.skyjo.domain.common.Carte;
import com.zenika.skyjo.domain.pioche.Pioche;
import com.zenika.skyjo.domain.pioche.PiocheVideException;

public class Plateau {

    private static final int LARGEUR = 4;
    private static final int HAUTEUR = 3;

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
        try {
            Carte[][] cartes = new Carte[LARGEUR][HAUTEUR];// TODO
            for (int ligne = 0; ligne < cartes.length; ligne++) {
                for (int colonne = 0; colonne < cartes[ligne].length; colonne++) {
                    cartes[ligne][colonne] = pioche.tirerProchaineCarte();
                }
            }
            return cartes;
        }catch (PiocheVideException e){
            throw new TropDeJoueursException();
        }
    }

    public Carte[][] getCartes() {
        return cartes;
    }

    public void setCarteEnMain(Carte carteEnMain) {
        this.carteEnMain = carteEnMain;
    }
}
