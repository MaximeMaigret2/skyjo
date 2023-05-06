package com.zenika.skyjo.application;

import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Defausse;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Pioche;
import com.zenika.skyjo.domain.Plateau;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.domain.exceptions.MancheInexistanteException;
import com.zenika.skyjo.domain.exceptions.NombreDeJoueursImpossible;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkyjoLogique {

    private final MancheRepository mancheRepository;

    public SkyjoLogique(MancheRepository mancheRepository) {
        this.mancheRepository = mancheRepository;
    }

    public Manche engagerUnJoueurSurUneManche(String mancheId, String nomJoueur, List<Position> positions) {
        Manche manche = recupererLaManche(mancheId);
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        retournerDeuxCartesFacesVisibles(positions, plateau);
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

    private void retournerDeuxCartesFacesVisibles(List<Position> positions, Plateau plateau) {
        positions.forEach(position -> plateau.carteEnPosition(position).retournerFaceVisible());
    }


    public Manche preparerUneManche(@NotNull List<String> joueurs) {
        if (joueurs.size() < 2 || joueurs.size() > 8) {
            throw new NombreDeJoueursImpossible();
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
        Manche manche = Manche.MancheBuilder.nouvelleManche()
                .avecDefausse(defausse)
                .avecPioche(pioche)
                .avecPlateau(plateauxJoueurs)
                .build();
        return mancheRepository.save(manche);
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

    public Manche unJoueurPiochePile(String mancheId, String nomJoueur) {
        Manche manche = recupererLaManche(mancheId);
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Carte cartePioche = tirerUneCarteDeLaPioche(manche);
        plateau.prendreUneCarteEnMain(cartePioche);
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

    public Manche unJoueurPiocheDefausse(String mancheId, String nomJoueur) {
        Manche manche = recupererLaManche(mancheId);
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Carte carteDefausse = tirerUneCarteDeLaDefausse(manche);
        plateau.prendreUneCarteEnMain(carteDefausse);
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

    public Manche unJoueurJoueEn(Position position, String mancheId, String nomJoueur) {
        Manche manche = recupererLaManche(mancheId);
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Carte carteAMettreDansLaDefausse = plateau.poserCarteEnMainEn(position);
        manche.defausser(carteAMettreDansLaDefausse);
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

    private Carte tirerUneCarteDeLaPioche(Manche manche) {
        return manche.getPioche().tirerUneCarte();
    }

    private Carte tirerUneCarteDeLaDefausse(Manche manche) {
        return manche.getDefausse().tirerUneCarte();
    }

    private Manche recupererLaManche(String mancheId) {
        return mancheRepository.findById(mancheId).orElseThrow(MancheInexistanteException::new);
    }
}
