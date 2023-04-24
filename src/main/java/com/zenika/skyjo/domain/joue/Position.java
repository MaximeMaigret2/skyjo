package com.zenika.skyjo.domain.joue;

public class Position {

    public final int colonne;
    public final int ligne;

    public static Position positionneEn(int colonne, int ligne){
        return new Position(colonne, ligne);
    }

    public Position(int colonne, int ligne) {
        this.colonne = colonne;
        this.ligne = ligne;
    }
}
