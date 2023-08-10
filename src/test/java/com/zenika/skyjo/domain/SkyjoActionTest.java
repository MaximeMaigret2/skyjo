package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.pile.Pile;
import com.zenika.skyjo.domain.pile.PileFactoryTestContext;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.zenika.skyjo.domain.pile.DistributionTestContext.distributionAvecCartePlaceeEn;
import static com.zenika.skyjo.domain.pile.DistributionTestContext.recupererDistributionSansImportance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SkyjoActionTest {


    private final SkyjoAction instance = new SkyjoAction(null);

    @Test
    void doit_piocher_pile_et_prendre_en_main(){
        Manche mancheMock = mock(Manche.class);

        Carte carteAPiocher = Carte.uneCarteDe(Valeur.MOINS_DEUX);
        List<Carte> cartes = List.of(carteAPiocher);
        Pile pile = PileFactoryTestContext.construireLaPiocheTestContext(new LinkedList<>(cartes));
        Plateau plateau = Plateau.creerPlateauPour("batmax", recupererDistributionSansImportance());

        when(mancheMock.recupererLePLateauDuJoueur(anyString())).thenReturn(plateau);
        when(mancheMock.getPioche()).thenReturn(pile);

        instance.piocherPile(mancheMock, "batmax");
        
        assertThat(pile.getCartes()).isEmpty();
        assertThat(plateau.getCarteEnMain()).isEqualTo(carteAPiocher);
    }

    @Test
    void doit_piocher_defausse_et_prendre_en_main(){
        Manche mancheMock = mock(Manche.class);

        Carte carteAPiocher = Carte.uneCarteDe(Valeur.DOUZE);
        List<Carte> cartes = List.of(carteAPiocher);
        Defausse defausse = new Defausse(new LinkedList<>(cartes));
        Plateau plateau = Plateau.creerPlateauPour("batmax", recupererDistributionSansImportance());

        when(mancheMock.recupererLePLateauDuJoueur(anyString())).thenReturn(plateau);
        when(mancheMock.getDefausse()).thenReturn(defausse);

        instance.piocherDefausse(mancheMock, "batmax");

        assertThat(defausse.getCartes()).isEmpty();
        assertThat(plateau.getCarteEnMain()).isEqualTo(carteAPiocher);
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

        Position position = new Position(3,2);

        Carte carte = Carte.uneCarteDe(Valeur.CINQ);
        Plateau plateau = Plateau.creerPlateauPour("batmax", distributionAvecCartePlaceeEn(carte, position));

        Carte carteEnMain = Carte.uneCarteDe(Valeur.HUIT);
        plateau.prendreUneCarteEnMain(carteEnMain);

        when(mancheMock.recupererLePLateauDuJoueur(anyString())).thenReturn(plateau);

        Carte resultat = instance.revelerEtRestituerCarteEnMain(position, mancheMock, "batmax");

        assertThat(carte.getStatut()).isEqualTo(Statut.VISIBLE);
        assertThat(resultat).isEqualTo(carteEnMain);
    }

}