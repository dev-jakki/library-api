package com.github.dev_jakki.library_api.controller.dto;

import com.github.dev_jakki.library_api.model.Autor;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();

        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);

        return  autor;
    }
}
