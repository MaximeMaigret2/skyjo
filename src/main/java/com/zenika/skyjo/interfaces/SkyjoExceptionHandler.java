package com.zenika.skyjo.interfaces;

import com.zenika.skyjo.domain.exceptions.CarteEnMainInexistanteEception;
import com.zenika.skyjo.domain.exceptions.MancheInexistanteException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class SkyjoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MancheInexistanteException.class)
    public void handleMancheInexistante(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(CarteEnMainInexistanteEception.class)
    public void handleCarteEnMainInexistante(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Veuillez prendre une carte en main, piocher pile ou défausse.");
    }

}