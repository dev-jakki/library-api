package com.github.dev_jakki.library_api.validator;

import com.github.dev_jakki.library_api.exceptions.InvalidFieldException;
import com.github.dev_jakki.library_api.exceptions.OperationNotAllowedException;
import com.github.dev_jakki.library_api.exceptions.RegisterDuplicateException;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if (isbnJaExistente(livro.getIsbn())) {
            throw new RegisterDuplicateException("ISBN já existente em outro livro");
        }

        if (isPrecoObrigatorio(livro)) {
            throw new InvalidFieldException("preco", "Livros publicados a partir de 2020 devem possuir preço");
        }
    }

    public boolean isbnJaExistente(String isbn) {
        return repository.existsByIsbn(isbn);
    }

    // Valida se o ano de publicação for 2020 ou superior e o preço do livro não foi informado
    public boolean isPrecoObrigatorio(Livro livro) {
        return livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO && livro.getPreco() == null;
    }
}
