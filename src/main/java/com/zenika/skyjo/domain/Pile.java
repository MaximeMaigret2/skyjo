package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.PiocheVideException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Pile {
    private final LinkedList<Carte> cartes;

    public Pile(LinkedList<Carte> cartes) {
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

    public static Pile construireLaPioche() {
        Pile pile = new Pile(new LinkedList<>());
        // Ajout de 5 cartes d'une valeur de -2
        ajoutDe5CartesValeurMoinsDeux(pile);
        // Ajout de 15 cartes d'une valeur de 0
        ajoutDe5CartesValeurZero(pile);
        // Ajout de 10 cartes d'une valeur de -1 a 12
        ajoutDe10CartesDeChaqueAutreValeur(pile);
        // Mélanger
        Collections.shuffle(pile.cartes);
        return pile;
    }

    private static void ajoutDe10CartesDeChaqueAutreValeur(Pile pile) {
        IntStream.range(0, 10).forEach(indexDix -> {
            // Ajout de -1
            pile.cartes.add(Carte.uneCarteDe(Valeur.MOINS_UN));
            // Ajout de 1,2,3,4,5,6,7,8,9,10,11,12
            pile.cartes.addAll(IntStream.range(1, 13).mapToObj(compteur -> Carte.uneCarteDe(Valeur.valeur(compteur))).toList());
        });
    }

    private static void ajoutDe5CartesValeurZero(Pile pile) {
        pile.cartes.addAll(IntStream.range(0, 15)
                .mapToObj(index ->
                        Carte.uneCarteDe(Valeur.ZERO)
                ).toList());
    }

    private static void ajoutDe5CartesValeurMoinsDeux(Pile pile) {
        pile.cartes.addAll(IntStream.range(0, 5)
                .mapToObj(index ->
                        Carte.uneCarteDe(Valeur.MOINS_DEUX)
                ).toList());
    }
}
