package com.zenika.skyjo.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zenika.skyjo.domain.Carte;
import com.zenika.skyjo.domain.Statut;
import com.zenika.skyjo.domain.Valeur;

import java.util.Objects;

import static com.zenika.skyjo.domain.Statut.VISIBLE;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CarteDto(Valeur valeur, Statut statut) {

    public static CarteDto fromDomain(Carte carte) {
        if (Objects.isNull(carte)) {
            return null;
        }
        return VISIBLE.equals(carte.getStatut()) ?
                new CarteDto(carte.getValeur(), carte.getStatut()) :
                new CarteDto(null, carte.getStatut());
    }

    @Override
    public String toString() {
        String carte = "[ ";
        if (Objects.nonNull(valeur)) {
            carte += valeur.getValeurNumerique();
        } else {
            carte += "skyjo";
        }
        carte += " ]";
        return carte;
    }
}
