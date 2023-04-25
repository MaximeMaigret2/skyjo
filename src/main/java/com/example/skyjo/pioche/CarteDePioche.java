package com.example.skyjo.pioche;

public class CarteDePioche {

    private final Valeur valeur;

    public static CarteDePioche uneCarteDe(Valeur valeur){
        return new CarteDePioche(valeur);
    }

    private CarteDePioche(Valeur valeur) {
        this.valeur = valeur;
    }

    public Valeur getValeur() {
        return valeur;
    }
}
