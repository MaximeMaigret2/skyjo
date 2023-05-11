package com.zenika.skyjo.interfaces.dto;

import com.github.freva.asciitable.AsciiTable;
import com.zenika.skyjo.domain.Etat;
import com.zenika.skyjo.domain.Manche;

import java.util.List;
import java.util.stream.Collectors;

public record MancheDto(String id, PiocheDto pioche, DefausseDto defausse, List<PlateauDto> plateaux, Etat etat) {

    public static MancheDto fromDomain(Manche manche) {
        List<PlateauDto> plateaux = manche.getPlateaux().stream().map(PlateauDto::fromDomain).toList();
        return new MancheDto(manche.getId(), PiocheDto.fromDomain(manche.getPioche()), DefausseDto.fromDomain(manche.getDefausse()), plateaux, manche.getEtat());
    }

    @Override
    public String toString() {
        String manche = "\n";
        manche += "ID : " + id + "\n";
        manche += "Défausse : " + defausse.cartes().getLast().toString() + "\n";
        manche += "Etat : " + etat + "\n";
        manche += plateaux.stream()
                .map(plateau -> "Joueur : " + plateau.joueur() + "\n" + "Carte en main : " + plateau.carteEnMain() + "\n" + AsciiTable.getTable(plateau.cartes())).collect(Collectors.joining("\n"));
        return manche;
    }
}
