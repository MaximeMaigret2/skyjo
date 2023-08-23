package com.zenika.skyjo.domain.pile;

import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.PlateauFactory;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.domain.Valeur;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.zenika.skyjo.domain.Carte.uneCarteDe;

public class DistributionTestContext {

    public static Distribution recupererDistributionSansImportance(){
        List<Carte> distributionNulle = genererToutesLesCartesAZero();
        return Distribution.recupererUneDistribution(distributionNulle);
    }

    private static List<Carte> genererToutesLesCartesAZero() {
        List<Carte> distributionNulle = new ArrayList<>();
        for(int i = 0; i < Distribution.TAILLE_DISTRIBUTION; i++){
            distributionNulle.add(uneCarteDe(Valeur.ZERO));
        }
        return distributionNulle;
    }

    public static Distribution distributionAvecCartePlaceeEn(Carte carteAPlacer, Position positionDemandee){
        List<Carte> distributionDemandee = new ArrayList<>();

        List<Carte> toutesLesCartesNulles = genererToutesLesCartesAZero();
        // On retire une carte qui sera remplacée par celle à placer
        toutesLesCartesNulles.remove(0);

        int i = 0;
        for (int ligne = 0; ligne < PlateauFactory.HAUTEUR; ligne++) {
            for (int colonne = 0; colonne < PlateauFactory.LARGEUR; colonne++) {
                if(positionDemandee.colonne() == colonne && positionDemandee.ligne() == ligne){
                    distributionDemandee.add(carteAPlacer);
                }else {
                    distributionDemandee.add(toutesLesCartesNulles.get(i));
                }
                i++;
            }
        }
        return Distribution.recupererUneDistribution(distributionDemandee);
    }
}
