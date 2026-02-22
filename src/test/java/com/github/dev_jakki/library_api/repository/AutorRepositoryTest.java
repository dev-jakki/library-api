package com.github.dev_jakki.library_api.repository;

import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(2021, 3, 24));

        var autorSalvo = repository.save(autor);
        System.out.println("Salvo com sucesso: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("e2e69460-69e4-4009-9f23-646916c0ed5e");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrato = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrato);

            autorEncontrato.setDataNascimento(LocalDate.of(1999, 5, 12));

            repository.save(autorEncontrato);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTeste() {
        System.out.println("Contagem de Autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("c8d97f8b-85a7-4e61-bbfc-5df4918b5009");

        repository.deleteById(id);
    }

    @Test
    public void deleteTodosTest() {
        repository.deleteAll();
    }

    @Test
    public void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Andre");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1894, 3, 24));

        Livro livro = new Livro();
        livro.setTitulo("Diário de um banana");
        livro.setIsbn("34-45-564");
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(50));
        livro.setDataPubliacacao(LocalDate.of(1999, 3, 12));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("Diário de um banana 2");
        livro2.setIsbn("345-3458-345");
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setPreco(BigDecimal.valueOf(60));
        livro2.setDataPubliacacao(LocalDate.of(2005, 3, 12));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros()); // Usando cascade só comentária essa linha
    }

    @Test
    void listarLivrosAutorTeste() {
        UUID id = UUID.fromString("4bfb6bd0-4927-4aac-aefc-e66df4866c53");
        var autor = repository.findById(id).get();

        // Buscar livros
        List<Livro> livros = livroRepository.findByAutor(autor);

        livros.forEach(System.out::println);
    }
}