package com.example.skyjo.pioche;

public class Carte {

    private final Valeur valeur;

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
