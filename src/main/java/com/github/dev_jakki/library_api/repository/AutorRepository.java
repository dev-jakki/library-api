package com.github.dev_jakki.library_api.repository;

import com.github.dev_jakki.library_api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    /* Das duas formas funciona para validar se existe nome,
       data nas e nacionalidade já cadastrados, porém um
       retorna Autor e outro só boleano
    */
    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade);
    // boolean existsByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade);
}
