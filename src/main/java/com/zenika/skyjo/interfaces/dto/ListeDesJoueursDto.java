package com.zenika.skyjo.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ListeDesJoueursDto(@NotNull @Size(min = 2, max = 8) List<String> joueurs) {
}
