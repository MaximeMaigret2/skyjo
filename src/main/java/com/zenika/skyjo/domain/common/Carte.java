package com.zenika.skyjo.domain.common;

import com.zenika.skyjo.domain.pioche.Valeur;

public class Carte {

    private final Valeur valeur;

    private Statut statut;

    public static Carte uneCarteDe(Valeur valeur){
        return new Carte(valeur);
    }

    private Carte(Valeur valeur) {
        this.valeur = valeur;
    }

    public Valeur getValeur() {
        return valeur;
    }
}
