package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoOrchestration;
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

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;

@RestController
@RequestMapping("/manches")
@Validated
public class PiocherControleur {

    private final SkyjoOrchestration deroulement;

    public PiocherControleur(SkyjoOrchestration deroulement) {
        this.deroulement = deroulement;
    }

    @PostMapping(path = "/{mancheId}/piocher/pile")
    public ResponseEntity<MancheDto> piochePile(@PathVariable String mancheId,
                                                @NotBlank @RequestHeader(JOUEUR) String joueur) {

        Manche manche = deroulement.unJoueurPiochePile(mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping(path = "/{mancheId}/piocher/defausse")
    public ResponseEntity<MancheDto> piocheDefausse(@PathVariable String mancheId,
                                                    @NotBlank @RequestHeader(JOUEUR) String joueur) {
        Manche manche = deroulement.unJoueurPiocheDefausse(mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }
}
