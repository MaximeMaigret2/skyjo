package com.zenika.skyjo.domain;

public class Carte {
    private final Valeur valeur;
    private Statut statut;

    public static Carte uneCarteDe(Valeur valeur) {
        return new Carte(valeur, Statut.CACHE);
    }

    private Carte(Valeur valeur, Statut statut) {
        this.valeur = valeur;
        this.statut = statut;
    }

    public void retournerFaceVisible() {
        this.statut = Statut.VISIBLE;
    }

    public Valeur getValeur() {
        return valeur;
    }

    public Statut getStatut() {
        return statut;
    }
}
