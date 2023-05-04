package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.DefausseVideException;

import java.util.LinkedList;

public record Defausse(LinkedList<Carte> cartes) {

    public Carte tirerUneCarte() throws DefausseVideException {
        if (cartes.size() == 0) {
            // Cela ne devrait pas arriver
            throw new DefausseVideException();
        }
        return cartes.removeLast();
    }

    public static Defausse construireLaDefausse(Carte carte) {
        Defausse defausse = new Defausse();
        // Forcer face visible
        carte.retournerFaceVisible();
        defausse.cartes.add(carte);
        return defausse;
    }

    private Defausse() {
        this(new LinkedList<>());
    }
}
