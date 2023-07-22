package com.zenika.skyjo.domain.pile;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.zenika.skyjo.domain.Valeur;

import java.io.IOException;

public class CartesAUtiliserDeserializer extends JsonDeserializer<CartesAUtiliser> {

    @Override
    public CartesAUtiliser deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        Valeur valeur = Valeur.valeur(Integer.parseInt(node.get("valeur").asText()));
        int occurences = node.get("nombre").asInt();

        return new CartesAUtiliser(valeur, occurences);
    }
}
