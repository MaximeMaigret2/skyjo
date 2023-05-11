package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.NombreDeJoueursImpossibleException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkyjoAction {

    public Manche engagerUnJoueurSurUneManche(Manche manche, String nomJoueur, List<Position> positions) {
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        retournerDeuxCartesFacesVisibles(positions, plateau);
        manche.verifierEtat();
        return manche;
    }

    private void retournerDeuxCartesFacesVisibles(List<Position> positions, Plateau plateau) {
        positions.forEach(position -> plateau.carteEnPosition(position).retournerFaceVisible());
    }


    public Manche preparerUneManche(@NotNull List<String> joueurs) {
        if (joueurs.size() < 2 || joueurs.size() > 8) {
            throw new NombreDeJoueursImpossibleException();
        }
        // Mise en place
        // Formez une pioche avec l'ensemble des cartes
        Pioche pioche = Pioche.construireLaPioche();
        // Chaque joueur reçoit 12 cartes face cachée
        List<Plateau> plateauxJoueurs = distribuerLesCartesAuxJoueurs(joueurs, pioche);
        // Révélez la première carte de la pioche, elle constitue le début de la pile de défausse
        Defausse defausse = retournerPremiereCarteDeLaDefausse(pioche);
        // La manche est en cours d'initialisation, en attente
        return initialiserLaManche(pioche, plateauxJoueurs, defausse);
    }

    private Manche initialiserLaManche(Pioche pioche, List<Plateau> plateauxJoueurs, Defausse defausse) {
        return Manche.MancheBuilder.nouvelleManche()
                .avecDefausse(defausse)
                .avecPioche(pioche)
                .avecPlateau(plateauxJoueurs)
                .build();
    }

    private Defausse retournerPremiereCarteDeLaDefausse(Pioche pioche) {
        Carte premiereCarteDeLaDefausse = pioche.tirerUneCarte();
        premiereCarteDeLaDefausse.retournerFaceVisible();
        return Defausse.construireLaDefausse(premiereCarteDeLaDefausse);
    }

    private List<Plateau> distribuerLesCartesAuxJoueurs(List<String> joueurs, Pioche pioche) {
        return joueurs.stream()
                .map(joueur -> Plateau.creerPlateauPour(joueur, pioche))
                .toList();
    }

    public Manche piocherPile(Manche manche, String nomJoueur) {
        // Les éléments de mon jeu
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Pioche pioche = manche.getPioche();
        // Le joueur interagit
        Carte cartePioche = pioche.tirerUneCarte();
        plateau.prendreUneCarteEnMain(cartePioche);
        manche.verifierEtat();
        return manche;
    }

    public Manche piocherDefausse(Manche manche, String nomJoueur) {
        // Les éléments de mon jeu
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Defausse defausse = manche.getDefausse();
        // Le joueur interagit
        Carte carteDefausse = defausse.tirerUneCarte();
        plateau.prendreUneCarteEnMain(carteDefausse);
        manche.verifierEtat();
        return manche;
    }

    public Carte echangerCarteEnMainDuJoueur(Position position, Manche manche, String nomJoueur) {
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        return plateau.echangerCarteEnMainAvec(position);
    }

    public Manche defausser(Manche manche, Carte carte) {
        Defausse defausse = manche.getDefausse();
        defausse.ajouterALaDefausse(carte);
        manche.verifierEtat();
        return manche;
    }

    public Carte revelerEtRestituerCarteEnMain(Position position, Manche manche, String nomJoueur) {
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        plateau.carteEnPosition(position).retournerFaceVisible();
        return plateau.restituerCarteEnMain();
    }
}
