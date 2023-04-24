package com.example.skyjo.pioche;

public enum Valeur {
    DOUZE(12),
    ONZE(11),
    DIX(10),
    NEUF(9),
    HUIT(8),
    SEPT(7),
    SIX(6),
    CINQ(5),
    QUATRE(4),
    TROIS(3),
    DEUX(2),
    UN(1),
    ZERO(0),
    MOINS_UN(-1),
    MOINS_DEUX(-2);

    private final int valeurNumerique;

    Valeur(int valeur) {
        this.valeurNumerique = valeur;
    }

    public static Valeur valeur(int valeur){
        for (Valeur value : Valeur.values()) {
            if(value.valeurNumerique == valeur){
                return value;
            }
        }
        throw new IllegalArgumentException("Les valeurs des cartes vont de \"-2\" à \"12\"");
    }
}
