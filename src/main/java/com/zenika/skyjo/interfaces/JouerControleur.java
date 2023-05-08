package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoDeroulement;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.NotImplementedException;
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

    private final SkyjoDeroulement service;

    public JouerControleur(SkyjoDeroulement service) {
        this.service = service;
    }

    @PostMapping("/{mancheId}/jouer/remplacer")
    public ResponseEntity<MancheDto> remplacerCarteEnMainSurLePlateau(@PathVariable String mancheId,
                                                                      @NotBlank @RequestHeader(JOUEUR) String joueur,
                                                                      @Valid @RequestBody Position position) {
        Manche manche = service.unJoueurJoueEn(position, mancheId, joueur);
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping("/{mancheId}/jouer/reveler")
    public ResponseEntity<MancheDto> revelerCartePlateau(@PathVariable String mancheId,
                                                         @NotBlank @RequestHeader(JOUEUR) String joueur,
                                                         @Valid @RequestBody Position position) {

        throw new NotImplementedException();
    }

    @PostMapping("/{mancheId}/jouer/deposer")
    public ResponseEntity<MancheDto> deposerSurLaDefausse(@PathVariable String mancheId,
                                                          @NotBlank @RequestHeader(JOUEUR) String joueur,
                                                          @Valid @RequestBody Position coup) {
        // ??? c'est un impact de quand on révèle ça nan ?
        throw new NotImplementedException();
    }
}
