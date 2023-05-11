package com.zenika.skyjo.domain;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SkyjoActionTest {


    private final SkyjoAction instance = new SkyjoAction();

    @Test
    void doit_piocher_pile_et_prendre_en_main(){
        Manche mancheMock = mock(Manche.class);
        Plateau plateauMock = mock(Plateau.class);

        Carte carteAPiocher = Carte.uneCarteDe(Valeur.MOINS_DEUX);
        List<Carte> cartes = List.of(carteAPiocher);
        Pile pile = new Pile(new LinkedList<>(cartes));

        when(mancheMock.recupererLePLateauDuJoueur(anyString())).thenReturn(plateauMock);
        when(mancheMock.getPioche()).thenReturn(pile);

        instance.piocherPile(mancheMock, "batmax");
        
        assertThat(pile.getCartes()).isEmpty();
        verify(plateauMock).prendreUneCarteEnMain(carteAPiocher);
    }

    @Test
    void doit_piocher_defausse_et_prendre_en_main(){
        Manche mancheMock = mock(Manche.class);
        Plateau plateauMock = mock(Plateau.class);

        Carte carteAPiocher = Carte.uneCarteDe(Valeur.DOUZE);
        List<Carte> cartes = List.of(carteAPiocher);
        Defausse defausse = new Defausse(new LinkedList<>(cartes));

        when(mancheMock.recupererLePLateauDuJoueur(anyString())).thenReturn(plateauMock);
        when(mancheMock.getDefausse()).thenReturn(defausse);

        instance.piocherDefausse(mancheMock, "batmax");

        assertThat(defausse.getCartes()).isEmpty();
        verify(plateauMock).prendreUneCarteEnMain(carteAPiocher);
    }

    @Test
    void doit_defausser_la_carte_en_main(){
        Manche mancheMock = mock(Manche.class);

        List<Carte> cartesDejaDefaussees = List.of(Carte.uneCarteDe(Valeur.MOINS_DEUX));
        Defausse defausse = new Defausse(new LinkedList<>(cartesDejaDefaussees));
        when(mancheMock.getDefausse()).thenReturn(defausse);

        Carte carte = Carte.uneCarteDe(Valeur.CINQ);
        instance.defausser(mancheMock, carte);

        assertThat(defausse.tirerUneCarte()).isEqualTo(carte);
    }

    @Test
    void doit_reveler_la_carte_en_position_donnee(){

        Manche mancheMock = mock(Manche.class);

        Plateau plateauMock = mock(Plateau.class);
        when(mancheMock.recupererLePLateauDuJoueur(any())).thenReturn(plateauMock);

        Position position = new Position(3,2);

        Carte carte = Carte.uneCarteDe(Valeur.CINQ);
        when(plateauMock.carteEnPosition(position)).thenReturn(carte);

        Carte carteEnMain = Carte.uneCarteDe(Valeur.HUIT);
        when(plateauMock.restituerCarteEnMain()).thenReturn(carteEnMain);

        Carte resultat = instance.revelerEtRestituerCarteEnMain(position, mancheMock, "batmax");

        assertThat(carte.getStatut()).isEqualTo(Statut.VISIBLE);
        assertThat(resultat).isEqualTo(carteEnMain);
    }

}