package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.Position;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manches")
public class JouerControleur {

    @PutMapping("/{mancheId}/jouer/remplacer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Manche> remplacer(@PathVariable String mancheId,
                                            @RequestBody Position coup) {

        throw new IllegalArgumentException();
    }
}
