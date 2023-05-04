package com.zenika.skyjo.common;

import com.zenika.skyjo.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MancheBuilderTest extends Manche.MancheBuilder {

    private final List<Carte> cartesPlateau = new ArrayList<>();
    private final List<Carte> cartesPioche = new ArrayList<>();
    private final LinkedList<Carte> cartesDefausse = new LinkedList<>();
    private String joueur;
    private final String id;
    private Carte carteEnMain = Carte.uneCarteDe(Valeur.ZERO); // defaut

    public MancheBuilderTest(String id) {
        this.id = id;
    }

    public MancheBuilderTest avecPlateau(Carte[][] plateau){
        List<Carte> cartesDuPlateau = Arrays.stream(plateau).flatMap(Stream::of).toList();
        cartesPlateau.addAll(cartesDuPlateau);
        return this;
    }

    public MancheBuilderTest avecPlateauQuelconqueCache(){
        // plateau rempli de 0
        cartesPlateau.addAll(IntStream.range(0, 12)
                .mapToObj(index ->
                        Carte.uneCarteDe(Valeur.ZERO)
                ).toList());
        return this;
    }

    public MancheBuilderTest avecPiocheFixee(List<Carte> cartes){
        cartesPioche.addAll(cartes);
        return this;
    }

    public MancheBuilderTest avecDefausseFixee(List<Carte> cartes){
        cartes.forEach(Carte::retournerFaceVisible);
        cartesPioche.addAll(cartes);
        return this;
    }

    public MancheBuilderTest avecDefausseFixee(Carte carte) {
        carte.retournerFaceVisible();
        cartesPioche.add(carte);
        return this;
    }

    public MancheBuilderTest pourJoueur(String joueur){
        this.joueur = joueur;
        return this;
    }

    public static MancheBuilderTest nouvelleMancheDeTest(String id){
        return new MancheBuilderTest(id);
    }

    public MancheBuilderTest avecCarteEnMain(Carte carteEnMain) {
        carteEnMain.retournerFaceVisible();
        this.carteEnMain = carteEnMain;
        return this;
    }

    public Manche build(){
        // la distribution va se faire avec les cartes choisies pour le plateau
        LinkedList<Carte> toutesLesCartes = new LinkedList<>();
        toutesLesCartes.addAll(cartesPlateau);
        toutesLesCartes.addAll(cartesPioche);
        Pioche pioche = new Pioche(toutesLesCartes);

        Manche manche = new Manche();
        manche.setId(id);
        manche.setDefausse(new Defausse(cartesDefausse));
        manche.setPioche(pioche);

        Plateau plateau = Plateau.creerPlateauPour(joueur, pioche);
        plateau.prendreUneCarteEnMain(carteEnMain);
        manche.setPlateaux(List.of(plateau));

        return manche;
    }

}
