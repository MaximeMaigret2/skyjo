package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.application.SkyjoDeroulement;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manches")
public class ConsulterControleur {
    private final SkyjoDeroulement deroulement;

    public ConsulterControleur(SkyjoDeroulement deroulement) {
        this.deroulement = deroulement;
    }

    @GetMapping("/{mancheId}")
    public ResponseEntity<MancheDto> demarrerUneNouvellePartie(@PathVariable String mancheId) {
        return ResponseEntity.ok(MancheDto.fromDomain(deroulement.recupererLaManche(mancheId)));
    }
}
