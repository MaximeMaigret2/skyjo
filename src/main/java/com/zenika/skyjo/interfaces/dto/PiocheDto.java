package com.zenika.skyjo.interfaces.dto;

import com.zenika.skyjo.domain.Pioche;

import java.util.LinkedList;
import java.util.stream.Collectors;

public record PiocheDto(LinkedList<CarteDto> cartes) {
    public static PiocheDto fromDomain(Pioche pioche) {
        LinkedList<CarteDto> cartes = pioche.cartes().stream().map(CarteDto::fromDomain).collect(Collectors.toCollection(LinkedList::new));
        return new PiocheDto(cartes);
    }
}
