package com.zenika.skyjo.domain.common;

import com.zenika.skyjo.domain.pioche.Pioche;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("parties")
public class Partie {

    @Id
    public String id;
    public Pioche pioche;

}
