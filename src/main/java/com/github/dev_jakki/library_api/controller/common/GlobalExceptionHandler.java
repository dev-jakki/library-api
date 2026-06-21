package com.github.dev_jakki.library_api.controller.common;

import com.github.dev_jakki.library_api.controller.dto.ErroCampo;
import com.github.dev_jakki.library_api.controller.dto.ErroResposta;
import com.github.dev_jakki.library_api.exceptions.InvalidFieldException;
import com.github.dev_jakki.library_api.exceptions.OperationNotAllowedException;
import com.github.dev_jakki.library_api.exceptions.RegisterDuplicateException;
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
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(f -> new ErroCampo(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação",
                listaErros
        );
    }

    @ExceptionHandler(RegisterDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegisterDuplicateException(RegisterDuplicateException e) {
        return ErroResposta.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperationNotAllowedException(OperationNotAllowedException e) {
        return ErroResposta.responseDefault(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handleInvalidFieldException(InvalidFieldException e) {
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação",
                List.of(new ErroCampo(e.getField(), e.getMessage()))
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleException(Exception e) {
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor. Tente novamente mais tarde.",
                List.of()
        );
    }

}
