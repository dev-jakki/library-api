package com.github.dev_jakki.library_api.service;

import com.github.dev_jakki.library_api.exceptions.OperationNotAllowedException;
import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.repository.AutorRepository;
import com.github.dev_jakki.library_api.repository.LivroRepository;
import com.github.dev_jakki.library_api.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Automatiza injeção no construtor (vem do Lombok)
public class AutorService {

    private final AutorRepository repository; // Injeção do service
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

//    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
//        this.repository = repository;
//        this.validator = validator;
//        this.livroRepository = livroRepository;
//    }

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Autor não existente");
        }

        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperationNotAllowedException("Não é permitido exluir um Autor que possui livros cadastrados!");
        }

        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null) {
            return repository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll(); // Se não passou nenhuma pesquisa/filtro, retorna todos
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
