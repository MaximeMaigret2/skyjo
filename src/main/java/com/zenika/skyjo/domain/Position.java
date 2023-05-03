package com.zenika.skyjo.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record Position(
        @Min(value = 0)
        @Max(value = 3)
        int colonne,
        @Min(value = 0)
        @Max(value = 2)
        int ligne) {
}
