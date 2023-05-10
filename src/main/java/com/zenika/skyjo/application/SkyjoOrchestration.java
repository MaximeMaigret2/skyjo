package com.zenika.skyjo.application;

import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.domain.SkyjoAction;
import com.zenika.skyjo.domain.exceptions.MancheInexistanteException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkyjoOrchestration {

    private final MancheRepository mancheRepository;
    private final SkyjoAction skyjoAction;

    public SkyjoOrchestration(MancheRepository mancheRepository, SkyjoAction skyjoAction) {
        this.mancheRepository = mancheRepository;
        this.skyjoAction = skyjoAction;
    }

    public Manche unJoueurEngageUneManche(String mancheId, String joueur, List<Position> positions) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoAction.engagerUnJoueurSurUneManche(manche, joueur, positions);
        return mancheRepository.save(manche);
    }

    public Manche unJoueurEchangeCarteEnMainEtDefausse(Position position, String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoAction.echangerCarteEnMainDuJoueurEtDefausser(position, manche, joueur);
        return mancheRepository.save(manche);
    }


    public Manche unJoueurDefausseCarteEnMainEtRevele(Position position, String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoAction.defausserCarteEnMainDuJoueurEtReveler(position, manche, joueur);
        return mancheRepository.save(manche);
    }

    public Manche desJoueursPreparerUneManche(List<String> joueurs) {
        Manche manche = skyjoAction.preparerUneManche(joueurs);
        return mancheRepository.save(manche);
    }

    public Manche unJoueurPiochePile(String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoAction.piocherPile(manche, joueur);
        return mancheRepository.save(manche);
    }

    public Manche unJoueurPiocheDefausse(String mancheId, String joueur) {
        Manche manche = recupererLaManche(mancheId);
        manche = skyjoAction.piocherDefausse(manche, joueur);
        return mancheRepository.save(manche);
    }

    public Manche recupererLaManche(String mancheId) {
        return mancheRepository.findById(mancheId).orElseThrow(MancheInexistanteException::new);
    }
}
