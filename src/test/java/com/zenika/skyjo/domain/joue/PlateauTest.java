package com.zenika.skyjo.domain.joue;

import com.zenika.skyjo.domain.common.Carte;
import com.zenika.skyjo.domain.pioche.Pioche;
import com.zenika.skyjo.domain.pioche.PiocheVideException;
import com.zenika.skyjo.domain.pioche.Valeur;
import org.junit.jupiter.api.Test;

import static com.zenika.skyjo.domain.common.Carte.uneCarteDe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlateauTest {

    @Test
    void on_doit_pouvoir_initialiser_un_plateau() throws PiocheVideException {

        Pioche pioche = mock(Pioche.class);
        when(pioche.tirerProchaineCarte()).thenReturn(uneCarteDe(Valeur.ZERO))
                .thenReturn(uneCarteDe(Valeur.UN))
                .thenReturn(uneCarteDe(Valeur.DEUX))
                .thenReturn(uneCarteDe(Valeur.TROIS))
                .thenReturn(uneCarteDe(Valeur.QUATRE))
                .thenReturn(uneCarteDe(Valeur.CINQ))
                .thenReturn(uneCarteDe(Valeur.SIX))
                .thenReturn(uneCarteDe(Valeur.SEPT))
                .thenReturn(uneCarteDe(Valeur.HUIT))
                .thenReturn(uneCarteDe(Valeur.NEUF))
                .thenReturn(uneCarteDe(Valeur.DIX))
                .thenReturn(uneCarteDe(Valeur.ONZE));

        Plateau plateau = Plateau.creerPlateauPour("Toto", pioche);

        Carte[][] cartes = plateau.getCartes();

        assertThat(cartes[0][0].getValeur()).isEqualTo(Valeur.ZERO);
        assertThat(cartes[0][1].getValeur()).isEqualTo(Valeur.UN);
        assertThat(cartes[0][2].getValeur()).isEqualTo(Valeur.DEUX);

        assertThat(cartes[1][0].getValeur()).isEqualTo(Valeur.TROIS);
        assertThat(cartes[1][1].getValeur()).isEqualTo(Valeur.QUATRE);
        assertThat(cartes[1][2].getValeur()).isEqualTo(Valeur.CINQ);

        assertThat(cartes[2][0].getValeur()).isEqualTo(Valeur.SIX);
        assertThat(cartes[2][1].getValeur()).isEqualTo(Valeur.SEPT);
        assertThat(cartes[2][2].getValeur()).isEqualTo(Valeur.HUIT);

        assertThat(cartes[3][0].getValeur()).isEqualTo(Valeur.NEUF);
        assertThat(cartes[3][1].getValeur()).isEqualTo(Valeur.DIX);
        assertThat(cartes[3][2].getValeur()).isEqualTo(Valeur.ONZE);
    }

}