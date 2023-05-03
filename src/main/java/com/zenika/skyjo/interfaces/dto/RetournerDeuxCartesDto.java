package com.zenika.skyjo.interfaces.dto;

import com.zenika.skyjo.domain.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RetournerDeuxCartesDto(@NotBlank
                                     String joueur,
                                     @NotNull
                                     @Size(min = 2, max = 2)
                                     List<Position> positions) {
}
