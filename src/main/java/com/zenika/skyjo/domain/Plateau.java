package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.CarteEnMainInexistanteEception;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Plateau {
    public static final int LARGEUR = 4;
    public static final int HAUTEUR = 3;

    private final Carte[][] cartes;
    private Carte carteEnMain;
    private final String joueur;

    protected Plateau(Carte[][] cartes, String joueur) {
        this.cartes = cartes;
        this.joueur = joueur;
    }

    public static Plateau creerPlateauPour(String joueur, Pioche pioche) {
        Carte[][] cartes = concevoirTableauDeCartes(pioche);
        return new Plateau(cartes, joueur);
    }


    private static Carte[][] concevoirTableauDeCartes(Pioche pioche) {
        Carte[][] cartes = new Carte[HAUTEUR][LARGEUR];
        IntStream.range(0, cartes.length).forEach(ligne ->
                IntStream.range(0, cartes[ligne].length).forEach(colonne ->
                        cartes[ligne][colonne] = pioche.tirerUneCarte())
        );
        return cartes;
    }

    public Carte[][] getCartes() {
        return cartes;
    }

    public Carte carteEnPosition(Position position) {
        return cartes[position.ligne()][position.colonne()];
    }

    public void prendreUneCarteEnMain(@NotNull Carte carteEnMain) {
        // Une carte en main est toujours visible
        carteEnMain.retournerFaceVisible();
        this.carteEnMain = carteEnMain;
    }

    public String getJoueur() {
        return joueur;
    }

    public Carte getCarteEnMain() {
        return carteEnMain;
    }

    public boolean plateauEngage() {
        // Le plateau a au moins deux cartes VISIBLE pour être engagé dans une manche
        return Arrays.stream(this.cartes)
                .flatMap(Arrays::stream)
                .filter(carte -> Statut.VISIBLE.equals(carte.getStatut()))
                .count() >= 2;
    }

    public boolean plateauComplete() {
        // Le plateau a exactement toutes les cartes VISIBLE, il est considéré comme complété
        return Arrays.stream(this.cartes)
                .flatMap(Arrays::stream)
                .allMatch(carte -> Statut.VISIBLE.equals(carte.getStatut()));
    }

    public Carte echangerCarteEnMainAvec(Position position) {
        // Switch des cartes
        Carte carteRemplacee = carteEnPosition(position);
        cartes[position.ligne()][position.colonne()] = restituerCarteEnMain();
        return carteRemplacee;
    }

    public Carte restituerCarteEnMain() {
        if (Objects.isNull(carteEnMain)) {
            throw new CarteEnMainInexistanteEception();
        }
        Carte carte = carteEnMain;
        // reset carte en main
        carteEnMain = null;
        return carte;
    }
}
