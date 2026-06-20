package com.github.dev_jakki.library_api.repository;

import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

//    @Test
//    void salvarCascadeTest() {
//        Livro livro = new Livro();
//        livro.setTitulo("Biblia");
//        livro.setIsbn("123-456-789");
//        livro.setGenero(GeneroLivro.BIOGRAFIA);
//        livro.setPreco(BigDecimal.valueOf(100));
//        livro.setDataPubliacacao(LocalDate.of(1990, 1, 1));
//
//        Autor autor = new Autor();
//        autor.setNome("Maria");
//        autor.setNacionalidade("Brasileira");
//        autor.setDataNascimento(LocalDate.of(2021, 3, 24));
//
//        livro.setAutor(autor);
//
//        repository.save(livro);
//    }

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setTitulo("As Cronicas de Narnia 2");
        livro.setIsbn("349-34-345");
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setPreco(BigDecimal.valueOf(540));
        livro.setDataPublicacao(LocalDate.of(1999, 3, 12));

        Autor autor = autorRepository
                .findById(UUID.fromString("d58d7689-14bc-48e1-a683-3923bf318cf3"))
                .orElse(null);

//        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarTest() {
        var id = UUID.fromString("14a3cbae-8f5e-4d17-94c0-b640a7b2433a");

        Optional<Livro> possivelLivro = repository.findById(id);

        if (possivelLivro.isPresent()) {
            Livro livroEncontrado = possivelLivro.get();
            System.out.println("Dados do livro:");
            System.out.println(livroEncontrado);

            livroEncontrado.setTitulo("As Cronicas de Narnia 3");

            repository.save(livroEncontrado);
        }
    }

    @Test
    void listarTest() {
        List<Livro> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    void deletePorIdTest() {
        var id = UUID.fromString("89835bd1-9383-40bf-b6a1-fccb0c05aa79");

        repository.deleteById(id);
    }

    @Test
    void atualizarAutorLivroTest() {
        UUID idLivro = UUID.fromString("90135f85-0e81-4a43-8c39-cb77fc747d99");
        var livroParaAtualizarAutor = repository.findById(idLivro).orElse(null);

        UUID idAutor = UUID.fromString("f91e7a86-0152-42b5-bf17-048449cf9bf9");
        var autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizarAutor.setAutor(autor);

        repository.save(livroParaAtualizarAutor);
    }

    @Test
    @Transactional
    void buscarLivroEAutorTest() {
        UUID id = UUID.fromString("90135f85-0e81-4a43-8c39-cb77fc747d99");

        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void buscarLivroTest() {
//        List<Livro> livros = repository.findByTitulo("Diário de um banana");
//        List<Livro> livros = repository.findByIsbn("123-456-789");
//        List<Livro> livros = repository.findByTituloAndPreco("Diário de um banana", BigDecimal.valueOf(50));
        List<Livro> livros = repository.findByTituloOrGenero("Diário de um banana", GeneroLivro.BIOGRAFIA);

        livros.forEach(System.out::println);
    }
}