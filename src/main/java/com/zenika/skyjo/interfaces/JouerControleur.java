package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoLogique;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.interfaces.dto.JoueurDto;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import jakarta.validation.Valid;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manches")
public class JouerControleur {
    private final SkyjoLogique skyjoLogique;

    public JouerControleur(SkyjoLogique skyjoLogique) {
        this.skyjoLogique = skyjoLogique;
    }

    @PostMapping("/{mancheId}/jouer/remplacer")
    public ResponseEntity<MancheDto> remplacerCarteEnMainSurLePlateau(@PathVariable String mancheId,
                                                                      @RequestBody Position position,
                                                                      @Valid @RequestHeader("joueur") JoueurDto joueurDto) {
        Manche manche = skyjoLogique.unJoueurJoueEn(position, mancheId, joueurDto.joueur());
        return ResponseEntity.ok(MancheDto.fromDomain(manche));
    }

    @PostMapping("/{mancheId}/jouer/reveler")
    public ResponseEntity<MancheDto> revelerCartePlateau(@PathVariable String mancheId,
                                                         @RequestBody Position position,
                                                         @Valid @RequestHeader("joueur") JoueurDto joueurDto) {

        throw new NotImplementedException();
    }

    @PostMapping("/{mancheId}/jouer/deposer")
    public ResponseEntity<MancheDto> deposerSurLaDefausse(@PathVariable String mancheId,
                                                          @RequestBody Position coup) {
        // ??? c'est un impact de quand on révèle ça nan ?
        throw new NotImplementedException();
    }
}
