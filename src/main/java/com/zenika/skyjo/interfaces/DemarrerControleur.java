package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoDeroulement;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.interfaces.dto.ListeDesJoueursDto;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import com.zenika.skyjo.interfaces.dto.DeuxPositionsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;

@RestController
@RequestMapping("/manches")
@Validated
public class DemarrerControleur {

    private final SkyjoDeroulement service;

    public DemarrerControleur(SkyjoDeroulement service) {
        this.service = service;
    }

    @PostMapping("/nouvellePartie")
    public ResponseEntity<MancheDto> demarrerUneNouvellePartie(@Valid @RequestBody ListeDesJoueursDto listeDesJoueursDto) {
        Manche manche = service.preparerUneManche(listeDesJoueursDto.joueurs());
        return ResponseEntity.created(URI.create("/manches/" + manche.getId())).body(MancheDto.fromDomain(manche));
    }

    @PostMapping("/{mancheId}/engager")
    public ResponseEntity<MancheDto> retournerDeuxCartes(@PathVariable String mancheId,
                                                         @NotBlank @RequestHeader(JOUEUR) String joueur,
                                                         @Valid @RequestBody DeuxPositionsDto deuxPositionsDto) {

        Manche manche = service.engagerUnJoueurSurUneManche(mancheId, joueur, deuxPositionsDto.positions());
        return ResponseEntity.ok().body(MancheDto.fromDomain(manche));
    }
}
