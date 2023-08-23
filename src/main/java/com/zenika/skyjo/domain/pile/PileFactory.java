package com.zenika.skyjo.domain.pile;

import com.zenika.skyjo.domain.Carte;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PileFactory {

    public static Pile construireLaPioche(List<CartesAUtiliser> cartesAUtilisers) {
        Pile pile = new Pile(new LinkedList<>());

        // Ajouter les cartes en main
        pile.getCartes().addAll(
                ajoutDesCartes(cartesAUtilisers));

        // Mélanger
        Collections.shuffle(pile.getCartes());
        return pile;
    }

    private static List<Carte> ajoutDesCartes(List<CartesAUtiliser> cartesAUtilisers) {
        return cartesAUtilisers.stream()
                .map(c -> IntStream.range(0, c.getOccurences())
                        .mapToObj(index -> Carte.uneCarteDe(c.getValeur()))
                        .collect(Collectors.toList())
                ).flatMap(Collection::stream)
                .toList();
    }

}
