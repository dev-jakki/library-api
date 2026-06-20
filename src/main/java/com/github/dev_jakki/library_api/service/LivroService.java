package com.github.dev_jakki.library_api.service;

import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro cadastrar(Livro livro) {
        return repository.save(livro);
    }

}
