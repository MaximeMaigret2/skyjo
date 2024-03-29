package com.zenika.skyjo.joue.pioche;

import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Valeur;
import com.zenika.skyjo.domain.exceptions.PiocheVideException;
import com.zenika.skyjo.domain.pile.CartesAUtiliser;
import com.zenika.skyjo.domain.pile.Pile;
import com.zenika.skyjo.domain.pile.PileFactory;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.zenika.skyjo.joue.pioche.PileTest.CarteAssert.assertThat;

class PileTest {

    @Test
    void doit_generer_toutes_les_cartes() {

        List<CartesAUtiliser> cartesAUtilisers = List.of(
                new CartesAUtiliser(Valeur.CINQ, 10),
                new CartesAUtiliser(Valeur.ZERO, 15),
                new CartesAUtiliser(Valeur.MOINS_DEUX, 5)
        );

        Pile pile = PileFactory.construireLaPioche(cartesAUtilisers);
        List<Carte> toutesLesCartes = new ArrayList<>();
        try {
            while (true) {
                toutesLesCartes.add(pile.tirerUneCarte());
            }
        } catch (PiocheVideException e) {
            assertThat(toutesLesCartes).hasNumberOfValeurs(10, Valeur.CINQ);
            assertThat(toutesLesCartes).hasNumberOfValeurs(15, Valeur.ZERO);
            assertThat(toutesLesCartes).hasNumberOfValeurs(5, Valeur.MOINS_DEUX);
        }
    }

    static class CarteAssert extends ListAssert<Carte> {

        public static CarteAssert assertThat(List<Carte> actual) {
            return new CarteAssert(actual);
        }

        public CarteAssert(List<Carte> actual) {
            super(actual);
        }

        public CarteAssert hasNumberOfValeurs(int expectedNumber, Valeur valeur) {
            long numberOfElement = actual.stream().filter(c -> c.getValeur().equals(valeur)).count();
            if (numberOfElement != expectedNumber) {
                failWithMessage("Expected having %s cards of %s value, but has %s", expectedNumber, valeur, numberOfElement);
            }
            return this;
        }
    }

}