package com.github.dev_jakki.library_api.repository;

import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    // O JPA vai gerar "select * from livro where id_autor = id"
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    // O JPA vai gerar "select * from livro where titulo = ? and preco = ?"
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // O JPA vai gerar "select * from livro where titulo = ? and genero = ?"
    List<Livro> findByTituloOrGenero(String titulo, Enum<GeneroLivro> genero);

    boolean existsByAutor(Autor autor);

}
