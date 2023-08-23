package com.zenika.skyjo.joue;

import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Plateau;
import com.zenika.skyjo.domain.Valeur;
import com.zenika.skyjo.domain.exceptions.PiocheVideException;
import com.zenika.skyjo.domain.pile.Distribution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.zenika.skyjo.domain.Carte.uneCarteDe;
import static org.assertj.core.api.Assertions.assertThat;

class PlateauTest {

    @Test
    void on_doit_pouvoir_initialiser_un_plateau() throws PiocheVideException {

        List<Carte> cartesDistribuees = List.of(uneCarteDe(Valeur.ZERO),
                uneCarteDe(Valeur.UN),
                uneCarteDe(Valeur.DEUX),
                uneCarteDe(Valeur.TROIS),
                uneCarteDe(Valeur.QUATRE),
                uneCarteDe(Valeur.CINQ),
                uneCarteDe(Valeur.SIX),
                uneCarteDe(Valeur.SEPT),
                uneCarteDe(Valeur.HUIT),
                uneCarteDe(Valeur.NEUF),
                uneCarteDe(Valeur.DIX),
                uneCarteDe(Valeur.ONZE));

        Distribution distributionDeToto = Distribution.recupererUneDistribution(cartesDistribuees);

        Plateau plateau = Plateau.creerPlateauPour("Toto", distributionDeToto);

        Carte[][] cartes = plateau.getCartes();

        assertThat(cartes[0][0].getValeur()).isEqualTo(Valeur.ZERO);
        assertThat(cartes[0][1].getValeur()).isEqualTo(Valeur.UN);
        assertThat(cartes[0][2].getValeur()).isEqualTo(Valeur.DEUX);
        assertThat(cartes[0][3].getValeur()).isEqualTo(Valeur.TROIS);

        assertThat(cartes[1][0].getValeur()).isEqualTo(Valeur.QUATRE);
        assertThat(cartes[1][1].getValeur()).isEqualTo(Valeur.CINQ);
        assertThat(cartes[1][2].getValeur()).isEqualTo(Valeur.SIX);
        assertThat(cartes[1][3].getValeur()).isEqualTo(Valeur.SEPT);

        assertThat(cartes[2][0].getValeur()).isEqualTo(Valeur.HUIT);
        assertThat(cartes[2][1].getValeur()).isEqualTo(Valeur.NEUF);
        assertThat(cartes[2][2].getValeur()).isEqualTo(Valeur.DIX);
        assertThat(cartes[2][3].getValeur()).isEqualTo(Valeur.ONZE);
    }

}