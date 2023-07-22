package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.exceptions.NombreDeJoueursImpossibleException;
import com.zenika.skyjo.domain.pile.PaquetCartes;
import com.zenika.skyjo.domain.pile.Pile;
import com.zenika.skyjo.domain.pile.PileFactory;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkyjoAction {

    private final PaquetCartes paquetCartes;

    public SkyjoAction(PaquetCartes paquetCartes) {
        this.paquetCartes = paquetCartes;
    }

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
        Pile pile = PileFactory.construireLaPioche(paquetCartes.cartesAUtilisers);
        // Chaque joueur reçoit 12 cartes face cachée
        List<Plateau> plateauxJoueurs = distribuerLesCartesAuxJoueurs(joueurs, pile);
        // Révélez la première carte de la pioche, elle constitue le début de la pile de défausse
        Defausse defausse = retournerPremiereCarteDeLaDefausse(pile);
        // La manche est en cours d'initialisation, en attente
        return initialiserLaManche(pile, plateauxJoueurs, defausse);
    }

    private Manche initialiserLaManche(Pile pile, List<Plateau> plateauxJoueurs, Defausse defausse) {
        return Manche.MancheBuilder.nouvelleManche()
                .avecDefausse(defausse)
                .avecPioche(pile)
                .avecPlateau(plateauxJoueurs)
                .build();
    }

    private Defausse retournerPremiereCarteDeLaDefausse(Pile pile) {
        Carte premiereCarteDeLaDefausse = pile.tirerUneCarte();
        premiereCarteDeLaDefausse.retournerFaceVisible();
        return Defausse.construireLaDefausse(premiereCarteDeLaDefausse);
    }

    private List<Plateau> distribuerLesCartesAuxJoueurs(List<String> joueurs, Pile pile) {
        return joueurs.stream()
                .map(joueur -> Plateau.creerPlateauPour(joueur, pile))
                .toList();
    }

    public Manche piocherPile(Manche manche, String nomJoueur) {
        // Les éléments de mon jeu
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Pile pile = manche.getPioche();
        // Le joueur interagit
        Carte cartePioche = pile.tirerUneCarte();
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
