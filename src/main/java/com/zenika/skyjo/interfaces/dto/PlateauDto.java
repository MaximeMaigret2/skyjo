package com.zenika.skyjo.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zenika.skyjo.domain.Plateau;

import java.util.stream.IntStream;

import static com.zenika.skyjo.domain.Plateau.HAUTEUR;
import static com.zenika.skyjo.domain.Plateau.LARGEUR;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlateauDto(CarteDto[][] cartes, CarteDto carteEnMain, String joueur) {
    public static PlateauDto fromDomain(Plateau plateau) {
        CarteDto[][] cartes = new CarteDto[HAUTEUR][LARGEUR];
        IntStream.range(0, cartes.length).forEach(ligne ->
                IntStream.range(0, cartes[ligne].length).forEach(colonne ->
                        cartes[ligne][colonne] = CarteDto.fromDomain(plateau.getCartes()[ligne][colonne]))
        );
        return new PlateauDto(cartes, CarteDto.fromDomain(plateau.getCarteEnMain()), plateau.getJoueur());
    }
}
