package com.zenika.skyjo.domain.pile;

import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.exceptions.PiocheVideException;

import java.util.LinkedList;


public class Pile {
    private final LinkedList<Carte> cartes;

    Pile(LinkedList<Carte> cartes) {
        this.cartes = cartes;
    }

    public LinkedList<Carte> getCartes() {
        return cartes;
    }

    public Carte tirerUneCarte() throws PiocheVideException {
        if (cartes.size() == 0) {
            throw new PiocheVideException();
        }
        return cartes.removeFirst();
    }


}
