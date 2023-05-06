package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoLogique;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.interfaces.dto.ListeDesJoueursDto;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import com.zenika.skyjo.interfaces.dto.RetournerDeuxCartesDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/manches")
public class DemarrerControleur {
    private final SkyjoLogique skyjoLogique;

    public DemarrerControleur(SkyjoLogique skyjoLogique) {
        this.skyjoLogique = skyjoLogique;
    }

    @PostMapping("/nouvellePartie")
    public ResponseEntity<MancheDto> demarrerUneNouvellePartie(@Valid @RequestBody ListeDesJoueursDto listeDesJoueursDto) {
        Manche manche = skyjoLogique.preparerUneManche(listeDesJoueursDto.joueurs());
        return ResponseEntity.created(URI.create("/manches/" + manche.getId())).body(MancheDto.fromDomain(manche));
    }

    @PostMapping("/{mancheId}/engager")
    public ResponseEntity<MancheDto> retournerDeuxCartes(@PathVariable String mancheId,
                                                         @Valid @RequestBody RetournerDeuxCartesDto retournerDeuxCartesDto) {
        Manche manche = skyjoLogique.engagerUnJoueurSurUneManche(mancheId, retournerDeuxCartesDto.joueur(), retournerDeuxCartesDto.positions());
        return ResponseEntity.ok().body(MancheDto.fromDomain(manche));
    }
}
