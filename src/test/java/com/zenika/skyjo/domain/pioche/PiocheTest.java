package com.zenika.skyjo.domain.pioche;

import com.zenika.skyjo.domain.common.Carte;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.zenika.skyjo.domain.pioche.PiocheTest.CarteAssert.assertThat;

class PiocheTest {

    @Test
    void doit_generer_toutes_les_cartes(){
        Pioche pioche = Pioche.creerPioche();
        List<Carte> toutesLesCartes = new ArrayList<>();
        try{
            while(true) {
                toutesLesCartes.add(pioche.tirerProchaineCarte());
            }
        }catch (PiocheVideException e){
            assertThat(toutesLesCartes).hasNumberOfValeurs(10, Valeur.CINQ);
            assertThat(toutesLesCartes).hasNumberOfValeurs(10, Valeur.MOINS_UN);
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

        public CarteAssert hasNumberOfValeurs(int expectedNumber, Valeur valeur){
            long numberOfElement = actual.stream().filter(c -> c.getValeur().equals(valeur)).count();
            if(numberOfElement!= expectedNumber){
                failWithMessage("Expected having %s cards of %s value, but has %s", expectedNumber, valeur, numberOfElement);
            }
            return this;
        }
    }

}