package com.zenika.skyjo.application;

import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.domain.SkyjoRegle;
import com.zenika.skyjo.domain.exceptions.MancheInexistanteException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkyjoDeroulement {

    private final MancheRepository mancheRepository;
    private final SkyjoRegle skyjoRegle;

    public SkyjoDeroulement(MancheRepository mancheRepository, SkyjoRegle skyjoRegle) {
        this.mancheRepository = mancheRepository;
        this.skyjoRegle = skyjoRegle;
    }

    public Manche engagerUnJoueurSurUneManche(String mancheId, String joueur, List<Position> positions) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoRegle.engagerUnJoueurSurUneManche(manche, joueur, positions);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche unJoueurJoueEn(Position position, String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoRegle.unJoueurJoueEn(position, manche, joueur);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche preparerUneManche(List<String> joueurs) {
        Manche manche = skyjoRegle.preparerUneManche(joueurs);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche unJoueurPiochePile(String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoRegle.unJoueurPiochePile(manche, joueur);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche unJoueurPiocheDefausse(String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        mancheRepository.save(manche);
        return manche;
    }

    public Manche recupererLaManche(String mancheId) {
        return mancheRepository.findById(mancheId).orElseThrow(MancheInexistanteException::new);
    }
}
