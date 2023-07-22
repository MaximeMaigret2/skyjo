package com.zenika.skyjo.domain.pile;

import com.zenika.skyjo.domain.Carte;

import java.util.LinkedList;
import java.util.List;

public class PileFactoryTestContext extends PileFactory{

    public static Pile construireLaPiocheTestContext(List<Carte> cartes){
        return new Pile(new LinkedList<>(cartes));
    }

}