package com.zenika.skyjo.interfaces.dto;

import com.zenika.skyjo.domain.Pile;

import java.util.LinkedList;
import java.util.stream.Collectors;

public record PiocheDto(LinkedList<CarteDto> cartes) {
    public static PiocheDto fromDomain(Pile pile) {
        LinkedList<CarteDto> cartes = pile.getCartes().stream().map(CarteDto::fromDomain).collect(Collectors.toCollection(LinkedList::new));
        return new PiocheDto(cartes);
    }
}
