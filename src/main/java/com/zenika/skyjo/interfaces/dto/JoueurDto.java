package com.zenika.skyjo.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

public record JoueurDto(@NotBlank String joueur) {
}
