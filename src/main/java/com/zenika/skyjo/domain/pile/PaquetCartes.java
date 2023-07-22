package com.zenika.skyjo.domain.pile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaquetCartes {

    @Value("${cartes}")
    public List<CartesAUtiliser> cartesAUtilisers;


}
