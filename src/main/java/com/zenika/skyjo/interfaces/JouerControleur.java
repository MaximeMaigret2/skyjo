package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manches")
public class JouerControleur {

    @PostMapping("/{mancheId}/jouer/remplacer")
    public ResponseEntity<MancheDto> remplacerCarteEnMainSurLePlateau(@PathVariable String mancheId,
                                                                      @RequestBody Position position) {

        throw new NotImplementedException();
    }

    @PostMapping("/{mancheId}/jouer/reveler")
    public ResponseEntity<MancheDto> revelerCartePlateau(@PathVariable String mancheId,
                                                         @RequestBody Position position) {

        throw new NotImplementedException();
    }

    @PostMapping("/{mancheId}/jouer/deposer")
    public ResponseEntity<MancheDto> deposerSurLaDefausse(@PathVariable String mancheId,
                                                          @RequestBody Position coup) {

        throw new NotImplementedException();
    }
}
