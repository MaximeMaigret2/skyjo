package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.JoueurInexistantException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("manches")
public class Manche {
    @Id
    private String id;
    private Pioche pioche;
    private Defausse defausse;
    private List<Plateau> plateaux = new ArrayList<>();
    private Etat etat;

    public Pioche getPioche() {
        return pioche;
    }

    public void setPioche(Pioche pioche) {
        this.pioche = pioche;
    }

    public List<Plateau> getPlateaux() {
        return plateaux;
    }

    public void setPlateaux(List<Plateau> plateaux) {
        this.plateaux = plateaux;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Defausse getDefausse() {
        return defausse;
    }

    public void setDefausse(Defausse defausse) {
        this.defausse = defausse;
    }

    public Plateau recupererLePLateauDuJoueur(@NotNull String nomJoueur) {
        return this.plateaux.stream()
                .filter(plateau -> nomJoueur.equalsIgnoreCase(plateau.getJoueur()))
                .findFirst()
                .orElseThrow(JoueurInexistantException::new);
    }

    public Etat verifierEtat() {
        if (this.plateaux.stream().anyMatch(Plateau::plateauComplete)) {
            setEtat(Etat.FINIE);
        } else if (this.plateaux.stream().allMatch(Plateau::plateauEngage)) {
            setEtat(Etat.ENCOURS);
        } else {
            setEtat(Etat.INITIALISATION);
        }
        return this.etat;
    }

    public static class MancheBuilder{
        private Pioche pioche;
        private Defausse defausse;
        private final List<Plateau> plateaux = new ArrayList<>();

        public static MancheBuilder nouvelleManche(){
            return new MancheBuilder();
        }

        public MancheBuilder avecPioche(Pioche pioche){
            this.pioche = pioche;
            return this;
        }

        public MancheBuilder avecDefausse(Defausse defausse){
            this.defausse = defausse;
            return this;
        }

        public MancheBuilder avecPlateau(List<Plateau> plateaux){
            this.plateaux.addAll(plateaux);
            return this;
        }

        public Manche build(){
            Manche manche = new Manche();
            manche.setDefausse(defausse);
            manche.setPioche(pioche);
            manche.setPlateaux(plateaux);
            manche.verifierEtat();
            return manche;
        }

    }
}
