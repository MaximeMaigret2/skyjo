package com.zenika.skyjo.interfaces.dto;

import com.zenika.skyjo.domain.Etat;
import com.zenika.skyjo.domain.Manche;

import java.util.List;

public record MancheDto(String id, PiocheDto pioche, DefausseDto defausse, List<PlateauDto> plateaux, Etat etat) {

    public static MancheDto fromDomain(Manche manche) {
        List<PlateauDto> plateaux = manche.getPlateaux().stream().map(PlateauDto::fromDomain).toList();
        return new MancheDto(manche.getId(), PiocheDto.fromDomain(manche.getPioche()), DefausseDto.fromDomain(manche.getDefausse()), plateaux, manche.getEtat());
    }
}
