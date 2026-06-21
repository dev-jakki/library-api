package com.github.dev_jakki.library_api.service;

import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.dev_jakki.library_api.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro cadastrar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public List<Livro> pesquisar(String isbn, String titulo, Integer anoPublicacao, GeneroLivro genero, String nomeAutor) {

        Specification<Livro> specs = Specification.where( (root, query, cb) ->  cb.conjunction() );

        if (isbn != null) specs = specs.and(isbnEqual(isbn));
        if (titulo != null) specs = specs.and(tituloLike(titulo));
        if (anoPublicacao != null) specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        if (genero != null) specs = specs.and(generoEqual(genero));
        if (nomeAutor != null) specs = specs.and(nomeAutorLike(nomeAutor));

        return repository.findAll(specs);

    }
}
