package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.domain.Position;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manches")
public class JouerControleur {

    @PostMapping("/{mancheId}/remplacer")
    public ResponseEntity<MancheDto> remplacerCarteEnMainSurLePlateau(@PathVariable String mancheId,
                                                                      @RequestBody Position position) {

        throw new NotImplementedException();
    }

    @PostMapping("/{mancheId}/reveler")
    public ResponseEntity<MancheDto> revelerCartePlateau(@PathVariable String mancheId,
                                                         @RequestBody Position position) {

        throw new NotImplementedException();
    }

    @PostMapping("/{mancheId}/deposer")
    public ResponseEntity<MancheDto> deposerSurLaDefausse(@PathVariable String mancheId,
                                                          @RequestBody Position coup) {

        throw new NotImplementedException();
    }
}
