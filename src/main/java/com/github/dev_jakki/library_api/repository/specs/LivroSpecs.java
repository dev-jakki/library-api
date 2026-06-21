package com.github.dev_jakki.library_api.repository.specs;

import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    // lower(livro.titulo) like lower('%:param%')
    public static Specification<Livro> tituloLike(String titulo) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero));
    }

    // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal( cb.function(
                        "to_char",
                        String.class,
                        root.get("dataPublicacao"),
                        cb.literal("YYYY")
                ), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like( cb.lower(joinAutor.get("nome")), "%" + nome.toLowerCase() + "%");

            // Aqui faz sempre JOIN, da forma acima posso especificar qual tipo de Join fazer
            // return cb.like( cb.lower(root.get("autor").get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }
//
//    public static Specification<Livro> anoPublicacaoSuperior2020(S) {
//        return (root, query, cb) ->
//                cb.equal( cb.function(
//                        "to_char",
//                        String.class,
//                        root.get("dataPublicacao"),
//                        cb.literal("YYYY")
//                ), anoPublicacao.toString());
//    }
}
