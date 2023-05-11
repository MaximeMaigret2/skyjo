package com.zenika.skyjo.domain;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class DefausseTest {

    private Defausse defausse;

    @Test
    void doit_ajouter_carte_dans_la_defausse(){
        defausse = new Defausse(new LinkedList<>());

        Carte carteADefausser = Carte.uneCarteDe(Valeur.DEUX);
        defausse.ajouterALaDefausse(carteADefausser);
        assertThat(defausse.getCartes()).contains(carteADefausser);
    }

}