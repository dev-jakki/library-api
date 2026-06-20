package com.github.dev_jakki.library_api.service;

import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.repository.AutorRepository;
import com.github.dev_jakki.library_api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar() {
        Autor autor = new Autor();
        autor.setNome("Mario");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2006, 3, 1));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setTitulo("Livro do Mario");
        livro.setIsbn("349-34-345");
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(540));
        livro.setDataPublicacao(LocalDate.of(1999, 3, 12));
        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Dona Maria")) {
            throw new RuntimeException("Rollback!");
        }
    }

    @Transactional
    void atualizacaoSemAtualizar() {
        UUID id = UUID.fromString("d55e02a2-543d-47f5-a510-b7f5550ecedf");

        Livro livro = livroRepository.findById(id).orElse(null);

        livro.setPreco(BigDecimal.valueOf(100));

        //livroRepository.save(livro); // Por ser um metodo Transactional, não precisa salvar manualmente
    }
}
