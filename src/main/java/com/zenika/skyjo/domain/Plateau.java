package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.CarteEnMainInexistanteException;
import com.zenika.skyjo.domain.pile.Distribution;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.Objects;

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

    public static Plateau creerPlateauPour(String joueur, @NotNull Distribution distribution) {

        Carte[][] cartes = PlateauFactory.concevoirTableauDeCartes(distribution);
        return new Plateau(cartes, joueur);
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
            throw new CarteEnMainInexistanteException();
        }
        Carte carte = carteEnMain;
        // reset carte en main
        carteEnMain = null;
        return carte;
    }
}
