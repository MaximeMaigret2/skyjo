package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoOrchestration;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.interfaces.dto.MancheDto;
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

import static com.zenika.skyjo.interfaces.HeaderConstants.JOUEUR;

@RestController
@RequestMapping("/manches")
@Validated
public class JouerControleur {

    private final SkyjoOrchestration deroulement;

    public JouerControleur(SkyjoOrchestration deroulement) {
        this.deroulement = deroulement;
    }

    @PostMapping("/{mancheId}/jouer/remplacer")
    public ResponseEntity<MancheDto> remplacerCarteEnMainSurLePlateau(@PathVariable String mancheId,
                                                                      @NotBlank @RequestHeader(JOUEUR) String joueur,
                                                                      @Valid @RequestBody Position position) {
        Manche manche = deroulement.unJoueurEchangeCarteEnMainEtDefausse(position, mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping("/{mancheId}/jouer/reveler")
    public ResponseEntity<MancheDto> defausserCarteEnMainRevelerCartePlateau(@PathVariable String mancheId,
                                                         @NotBlank @RequestHeader(JOUEUR) String joueur,
                                                         @Valid @RequestBody Position position) {

        Manche manche = deroulement.unJoueurDefausseCarteEnMainEtRevele(position, mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }
}
