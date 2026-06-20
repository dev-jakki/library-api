package com.github.dev_jakki.library_api.controller.common;

import com.github.dev_jakki.library_api.controller.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<com.github.dev_jakki.library_api.controller.dto.FieldError> listaErros = fieldErrors
                .stream()
                .map(f -> new com.github.dev_jakki.library_api.controller.dto.FieldError(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", listaErros);
    }

}
