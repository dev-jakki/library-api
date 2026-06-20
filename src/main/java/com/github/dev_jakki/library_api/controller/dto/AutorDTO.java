package com.github.dev_jakki.library_api.controller.dto;

import com.github.dev_jakki.library_api.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 100, message = "Campo aceita de 2 a 100 caracteres")
        String nome,
        @NotNull(message = "Campo obrigatório")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 50, message = "Campo aceita de 2 a 50 caracteres")
        String nacionalidade
) { }
