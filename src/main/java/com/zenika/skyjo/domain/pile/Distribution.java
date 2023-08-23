package com.zenika.skyjo.domain.pile;

import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.PlateauFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente les 12 cartes distribuées à un joueur pour faire son plateau
 */
public class Distribution {

    public final static int TAILLE_DISTRIBUTION = PlateauFactory.HAUTEUR * PlateauFactory.LARGEUR;

    private final List<Carte> cartesDistribuees = new ArrayList<>(TAILLE_DISTRIBUTION);

    private Distribution(){

    }

    public static Distribution recupererUneDistribution(List<Carte> cartes){
        Distribution distribution = new Distribution();
        if(cartes.size() > TAILLE_DISTRIBUTION){
            throw new RuntimeException("todo");
        }
        distribution.cartesDistribuees.addAll(cartes);
        return distribution;
    }

    public List<Carte> getCartesDistribuees(){
        return cartesDistribuees;
    }

}
