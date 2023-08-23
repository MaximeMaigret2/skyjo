package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.pile.Distribution;

public class PlateauFactory {

    public static final int LARGEUR = 4;
    public static final int HAUTEUR = 3;

    public static Carte[][] concevoirTableauDeCartes(Distribution cartesDistribuees) {
        Carte[][] cartes = new Carte[HAUTEUR][LARGEUR];
        int i = 0;
        for (int ligne = 0; ligne < cartes.length; ligne++) {
            for (int colonne = 0; colonne < cartes[ligne].length; colonne++) {
                cartes[ligne][colonne] = cartesDistribuees.getCartesDistribuees().get(i);
                i++;
            }
        }
        return cartes;
    }
}
