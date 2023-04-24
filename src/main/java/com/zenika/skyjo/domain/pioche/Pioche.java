package com.zenika.skyjo.domain.pioche;

import com.zenika.skyjo.domain.common.Carte;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.stream.IntStream;

public class Pioche{

    private final LinkedList<Carte> cartes = new LinkedList<>();

    public Carte tirerProchaineCarte() throws PiocheVideException {
        if(cartes.size() == 0){
            throw new PiocheVideException();
        }
        Carte first = cartes.getFirst();
        cartes.remove(first); // TODO : gérer quand pioche vide
        return first;
    }

    public static Pioche creerPioche() {
        Pioche pioche = new Pioche();
        // Ajout de 5 cartes d'une valeur de -2
        ajoutDe5CartesValeurMoinsDeux(pioche);
        // Ajout de 15 cartes d'une valeur de 0
        ajoutDe5CartesValeurZero(pioche);

        // Ajout de 10 cartes d'une valeur de -1 a 12
        ajoutDe10CartesDeChaqueAutreValeur(pioche);

        //TODO mélanger
        return pioche;
    }

    private static void ajoutDe10CartesDeChaqueAutreValeur(Pioche pioche) {
        IntStream.range(0, 10).forEach(indexDix -> {
            // Ajout de -1
            pioche.cartes.add(Carte.uneCarteDe(Valeur.MOINS_UN));
            // Ajout de 1,2,3,4,5,6,7,8,9,10,11,12
            pioche.cartes.addAll(IntStream.range(1, 13).mapToObj(compteur -> Carte.uneCarteDe(Valeur.valeur(compteur))).toList());
        });
    }

    private static void ajoutDe5CartesValeurZero(Pioche pioche) {
        pioche.cartes.addAll(IntStream.range(0, 15)
                .mapToObj(index ->
                        Carte.uneCarteDe(Valeur.ZERO)
                ).toList());
    }

    private static void ajoutDe5CartesValeurMoinsDeux(Pioche pioche) {
        pioche.cartes.addAll(IntStream.range(0, 5)
                .mapToObj(index ->
                        Carte.uneCarteDe(Valeur.MOINS_DEUX)
                ).toList());
    }
}
