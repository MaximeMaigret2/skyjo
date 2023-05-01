package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoLogique;
import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.interfaces.dto.JoueurDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manches")
public class PiocherControleur {
    private final SkyjoLogique skyjoLogique;

    public PiocherControleur(SkyjoLogique skyjoLogique) {
        this.skyjoLogique = skyjoLogique;
    }

    @GetMapping(path = "/{mancheId}/piocher")
    public ResponseEntity<Manche> pioche(@PathVariable String mancheId,
                                         @Valid @RequestBody JoueurDto joueurDto) {
        return ResponseEntity.ok(skyjoLogique.unJoueurPioche(mancheId, joueurDto.joueur()));
    }
}
