package com.github.dev_jakki.library_api.service;

import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }
}
