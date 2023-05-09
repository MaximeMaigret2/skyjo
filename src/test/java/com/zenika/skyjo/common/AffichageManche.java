package com.zenika.skyjo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.skyjo.interfaces.dto.MancheDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.io.IOException;
import java.util.function.Consumer;

public final class AffichageManche {
    private static final Logger LOGGER = LoggerFactory.getLogger(AffichageManche.class);

    public static Consumer<EntityExchangeResult<byte[]>> convertirEtAfficher() {
        return entityExchangeResult -> {
            try {
                afficherManche(new ObjectMapper().readValue(entityExchangeResult.getResponseBody(), MancheDto.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void afficherManche(MancheDto manche) {
        LOGGER.info(String.valueOf(manche));
    }
}
