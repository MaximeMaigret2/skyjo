package com.zenika.skyjo.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zenika.skyjo.domain.Plateau;

import java.util.stream.IntStream;

import static com.zenika.skyjo.domain.Plateau.HAUTEUR;
import static com.zenika.skyjo.domain.Plateau.LARGEUR;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlateauDto(CarteDto[][] cartes, CarteDto carteEnMain, String joueur) {
    public static PlateauDto fromDomain(Plateau plateau) {
        CarteDto[][] cartes = new CarteDto[LARGEUR][HAUTEUR];
        IntStream.range(0, cartes.length).forEach(colonne ->
                IntStream.range(0, cartes[colonne].length).forEach(ligne ->
                        cartes[colonne][ligne] = CarteDto.fromDomain(plateau.getCartes()[colonne][ligne]))
        );
        return new PlateauDto(cartes, CarteDto.fromDomain(plateau.getCarteEnMain()), plateau.getJoueur());
    }
}
