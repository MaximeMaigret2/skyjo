package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.domain.Manche;
import com.zenika.skyjo.domain.MancheRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manches")
public class ConsulterControleur {

    private final MancheRepository mancheRepository;

    public ConsulterControleur(MancheRepository mancheRepository) {
        this.mancheRepository = mancheRepository;
    }

    @GetMapping("/{mancheId}")
    public ResponseEntity<Manche> demarrerUneNouvellePartie(@PathVariable String mancheId) {
        return ResponseEntity.of(mancheRepository.findById(mancheId));
    }
}
