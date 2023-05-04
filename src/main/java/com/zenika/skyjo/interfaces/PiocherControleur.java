package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoService;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.interfaces.dto.JoueurDto;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manches")
public class PiocherControleur {

    private final SkyjoService service;

    public PiocherControleur(SkyjoService service) {
        this.service = service;
    }

    @PostMapping(path = "/{mancheId}/piocher/pile")
    public ResponseEntity<MancheDto> piochePile(@PathVariable String mancheId,
                                                @Valid @RequestHeader("joueur") JoueurDto joueurDto) {

        Manche manche = service.unJoueurPiochePile(mancheId, joueurDto.joueur());
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping(path = "/{mancheId}/piocher/defausse")
    public ResponseEntity<MancheDto> piocheDefausse(@PathVariable String mancheId,
                                                    @Valid @RequestHeader("joueur") JoueurDto joueurDto) {
        Manche manche = service.unJoueurPiocheDefausse(mancheId, joueurDto.joueur());
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }
}
