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
        Manche manche = mancheRepository.findById(mancheId).orElseThrow(MancheInexistanteException::new);
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        positions.forEach(position -> plateau.carteEnPosition(position).retournerFaceVisible());
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

    public Manche preparerUneManche(@NotNull List<String> joueurs) {
        if (joueurs.size() < 2 || joueurs.size() > 8) {
            throw new NombreDeJoueursImpossible();
        }
        // Mise en place
        // Formez une pioche avec l'ensemble des cartes
        Pioche pioche = Pioche.construireLaPioche();
        // Chaque joueur reçoit 12 cartes face cachée
        List<Plateau> plateauxJoueurs = joueurs.stream()
                .map(joueur -> Plateau.creerPlateauPour(joueur, pioche))
                .toList();
        // Révélez la première carte de la pioche, elle constitue le début de la pile de défausse
        Carte premiereCarteDeLaDefausse = pioche.tirerUneCarte();
        premiereCarteDeLaDefausse.retournerFaceVisible();
        Defausse defausse = Defausse.construireLaDefausse(premiereCarteDeLaDefausse);
        // La manche est en cours d'initialisation, en attente
        Manche manche = new Manche();
        manche.setDefausse(defausse);
        manche.setPioche(pioche);
        manche.setPlateaux(plateauxJoueurs);
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

    public Manche unJoueurPioche(String mancheId, String nomJoueur) {
        Manche manche = mancheRepository.findById(mancheId).orElseThrow(MancheInexistanteException::new);
        Plateau plateau = manche.recupererLePLateauDuJoueur(nomJoueur);
        Carte cartePioche = manche.getPioche().tirerUneCarte();
        plateau.prendreUneCarteEnMain(cartePioche);
        manche.verifierEtat();
        return mancheRepository.save(manche);
    }

}