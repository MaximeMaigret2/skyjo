package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoDeroulement;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manches")
@Validated
public class PiocherControleur {

    private final SkyjoDeroulement service;

    public PiocherControleur(SkyjoDeroulement service) {
        this.service = service;
    }

    @PostMapping(path = "/{mancheId}/piocher/pile")
    public ResponseEntity<MancheDto> piochePile(@PathVariable String mancheId,
                                                @NotBlank @RequestHeader("joueur") String joueur) {

        Manche manche = service.unJoueurPiochePile(mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping(path = "/{mancheId}/piocher/defausse")
    public ResponseEntity<MancheDto> piocheDefausse(@PathVariable String mancheId,
                                                    @NotBlank @RequestHeader("joueur") String joueur) {
        Manche manche = service.unJoueurPiocheDefausse(mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }
}
