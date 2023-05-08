package com.zenika.skyjo.common;

import com.zenika.skyjo.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MancheTestBuilder extends Manche.MancheBuilder {

    private final List<Carte> cartesPlateau = new ArrayList<>();
    private final List<Carte> cartesPioche = new ArrayList<>();
    private final LinkedList<Carte> cartesDefausse = new LinkedList<>();
    private String joueur;
    private final String id;
    private Carte carteEnMain = Carte.uneCarteDe(Valeur.ZERO); // defaut

    public MancheTestBuilder(String id) {
        this.id = id;
    }

    public MancheTestBuilder avecPlateau(Carte[][] plateau){
        List<Carte> cartesDuPlateau = Arrays.stream(plateau).flatMap(Stream::of).toList();
        cartesPlateau.addAll(cartesDuPlateau);
        return this;
    }

    public MancheTestBuilder avecPlateauQuelconqueCache(){
        // plateau rempli de 0
        cartesPlateau.addAll(IntStream.range(0, 12)
                .mapToObj(index ->
                        Carte.uneCarteDe(Valeur.ZERO)
                ).toList());
        return this;
    }

    public MancheTestBuilder avecPiocheFixee(List<Carte> cartes){
        cartesPioche.addAll(cartes);
        return this;
    }

    public MancheTestBuilder avecDefausseFixee(List<Carte> cartes){
        cartes.forEach(Carte::retournerFaceVisible);
        cartesPioche.addAll(cartes);
        return this;
    }

    public MancheTestBuilder avecDefausseFixee(Carte carte) {
        carte.retournerFaceVisible();
        cartesPioche.add(carte);
        return this;
    }

    public MancheTestBuilder pourJoueur(String joueur){
        this.joueur = joueur;
        return this;
    }

    public static MancheTestBuilder nouvelleMancheDeTest(String id){
        return new MancheTestBuilder(id);
    }

    public MancheTestBuilder avecCarteEnMain(Carte carteEnMain) {
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
