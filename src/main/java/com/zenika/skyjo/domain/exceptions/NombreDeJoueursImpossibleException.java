package com.zenika.skyjo.domain.exceptions;

public class NombreDeJoueursImpossibleException extends RuntimeException {
    public NombreDeJoueursImpossibleException() {
        super("Le nombre de joueurs doit être compris entre 2 et 8 joueurs");
    }
}
