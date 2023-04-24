package com.example.skyjo.pioche;

import org.springframework.stereotype.Service;

import static com.example.skyjo.pioche.Carte.uneCarteDe;
import static com.example.skyjo.pioche.Valeur.valeur;

@Service
public class PileMock implements Pile{

    @Override
    public Carte tirerProchaineCarte() {
        return uneCarteDe(valeur(12)); // mock
    }
}
