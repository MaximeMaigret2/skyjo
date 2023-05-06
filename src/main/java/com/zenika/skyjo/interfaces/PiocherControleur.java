package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoLogique;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.interfaces.dto.JoueurDto;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manches")
public class PiocherControleur {
    private final SkyjoLogique skyjoLogique;

    public PiocherControleur(SkyjoLogique skyjoLogique) {
        this.skyjoLogique = skyjoLogique;
    }

    @PostMapping(path = "/{mancheId}/piocher/pile")
    public ResponseEntity<MancheDto> piochePile(@PathVariable String mancheId,
                                                @Valid @RequestHeader("joueur") JoueurDto joueurDto) {
        Manche manche = skyjoLogique.unJoueurPiochePile(mancheId, joueurDto.joueur());
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping(path = "/{mancheId}/piocher/defausse")
    public ResponseEntity<MancheDto> piocheDefausse(@PathVariable String mancheId,
                                                    @Valid @RequestHeader("joueur") JoueurDto joueurDto) {
        Manche manche = skyjoLogique.unJoueurPiocheDefausse(mancheId, joueurDto.joueur());
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }
}
