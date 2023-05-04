package com.zenika.skyjo.application;

import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.domain.SkyjoLogique;
import com.zenika.skyjo.domain.exceptions.MancheInexistanteException;
import com.zenika.skyjo.interfaces.repository.MancheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkyjoService {

    private final MancheRepository mancheRepository;
    private final SkyjoLogique skyjoLogique;

    public SkyjoService(MancheRepository mancheRepository) {
        this.mancheRepository = mancheRepository;
        skyjoLogique = SkyjoLogique.getInstance();
    }

    public Manche engagerUnJoueurSurUneManche(String mancheId, String joueur, List<Position> positions) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoLogique.engagerUnJoueurSurUneManche(manche, joueur, positions);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche unJoueurJoueEn(Position position, String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoLogique.unJoueurJoueEn(position, manche, joueur);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche preparerUneManche(List<String> joueurs) {
        Manche manche = skyjoLogique.preparerUneManche(joueurs);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche unJoueurPiochePile(String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoLogique.unJoueurPiochePile(manche, joueur);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche unJoueurPiocheDefausse(String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);

        mancheRepository.save(manche);
        return manche;
    }

    private Manche recupererLaManche(String mancheId) {
        return mancheRepository.findById(mancheId).orElseThrow(MancheInexistanteException::new);
    }
}
