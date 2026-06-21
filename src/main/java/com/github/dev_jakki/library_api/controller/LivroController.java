package com.github.dev_jakki.library_api.controller;

import com.github.dev_jakki.library_api.controller.dto.AutorDTO;
import com.github.dev_jakki.library_api.controller.dto.RegisterBookDTO;
import com.github.dev_jakki.library_api.controller.dto.ResultSearchBookDTO;
import com.github.dev_jakki.library_api.controller.mappers.LivroMapper;
import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid RegisterBookDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.cadastrar(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultSearchBookDTO> obterPorId(@PathVariable("id") String id) {
        UUID idLivro = UUID.fromString(id);

        return service
                .obterPorId(idLivro)
                .map( livro -> {
                    ResultSearchBookDTO dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        UUID idLivro = UUID.fromString(id);

        return service
                .obterPorId(idLivro)
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultSearchBookDTO>> pesquisar(
            @RequestParam(name = "isbn", required = false)
            String isbn,
            @RequestParam(name = "titulo", required = false)
            String titulo,
            @RequestParam(name = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(name = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(name = "nome-autor", required = false)
            String nomeAutor
    ) {
        var result = service.pesquisar(isbn, titulo, anoPublicacao, genero, nomeAutor);

        List<ResultSearchBookDTO> livros = result
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(livros);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id")
            String id,
            @RequestBody @Valid
            RegisterBookDTO dto
    ) {
        UUID idLivro = UUID.fromString(id);

        return service
                .obterPorId(idLivro)
                .map(livro -> {
                    Livro l = mapper.toEntity(dto);

                    livro.setDataPublicacao(l.getDataPublicacao());
                    livro.setIsbn(l.getIsbn());
                    livro.setTitulo(l.getTitulo());
                    livro.setGenero(l.getGenero());
                    livro.setPreco(l.getPreco());
                    livro.setAutor(l.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
