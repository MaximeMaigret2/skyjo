package com.zenika.skyjo.domain.pile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zenika.skyjo.domain.Valeur;

@JsonDeserialize(using = CartesAUtiliserDeserializer.class)
public class CartesAUtiliser {

    private final Valeur valeur;

    private final int occurences;

    public CartesAUtiliser(Valeur valeur, int occurences) {
        this.valeur = valeur;
        this.occurences = occurences;
    }

    public Valeur getValeur() {
        return valeur;
    }

    public int getOccurences() {
        return occurences;
    }
}
