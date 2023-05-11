package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.DefausseVideException;

import java.util.LinkedList;

public class Defausse {
    private final LinkedList<Carte> cartes;

    public Defausse(LinkedList<Carte> cartes) {
        this.cartes = cartes;
    }

    public LinkedList<Carte> getCartes() {
        return cartes;
    }

    public Carte tirerUneCarte() throws DefausseVideException {
        if (cartes.size() == 0) {
            // Cela ne devrait pas arriver
            throw new DefausseVideException();
        }
        return cartes.removeFirst();
    }

    public static Defausse construireLaDefausse(Carte carte) {
        Defausse defausse = new Defausse(new LinkedList<>());
        // Forcer face visible
        carte.retournerFaceVisible();
        defausse.cartes.add(carte);
        return defausse;
    }

    public void ajouterALaDefausse(Carte carteADefausser) {
        cartes.addFirst(carteADefausser);
    }
}
