package com.github.dev_jakki.library_api.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String message, List<ErroCampo> errors) {

    public static ErroResposta responseDefault(String message) {
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErroResposta conflict(String message) {
        return new ErroResposta(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
