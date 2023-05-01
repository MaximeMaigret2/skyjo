package com.zenika.skyjo.domain.exceptions;

public class NombreDeJoueursImpossible extends RuntimeException {
    public NombreDeJoueursImpossible() {
        super("Le nombre de joueurs doit être compris entre 2 et 8 joueurs");
    }
}
