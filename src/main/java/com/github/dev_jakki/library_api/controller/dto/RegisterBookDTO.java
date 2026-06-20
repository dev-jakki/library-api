package com.github.dev_jakki.library_api.controller.dto;

import com.github.dev_jakki.library_api.model.GeneroLivro;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterBookDTO(
        @NotBlank(message = "Campo obrigatório")
        @ISBN
        String isbn,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 150, message = "Campo aceita de 3 a 150 caracteres")
        String titulo,

        @NotNull(message = "Campo obrigatório")
        @PastOrPresent(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,

        @NotNull(message = "Campo obrigatório")
        GeneroLivro genero,

        @NotNull(message = "Campo não pode ser nulo")
        @DecimalMin("0.00")
        @Digits(integer = 8, fraction = 2)
        BigDecimal preco,

        @NotNull(message = "Campo obrigatório")
        UUID idAutor
) { }
