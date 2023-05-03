package com.zenika.skyjo.interfaces.dto;

import com.zenika.skyjo.domain.Defausse;

import java.util.LinkedList;
import java.util.stream.Collectors;

public record DefausseDto(LinkedList<CarteDto> cartes) {
    public static DefausseDto fromDomain(Defausse defausse) {
        LinkedList<CarteDto> cartes = defausse.cartes().stream().map(CarteDto::fromDomain).collect(Collectors.toCollection(LinkedList::new));
        return new DefausseDto(cartes);
    }
}
