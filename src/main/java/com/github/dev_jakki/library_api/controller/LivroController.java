package com.github.dev_jakki.library_api.controller;

import com.github.dev_jakki.library_api.controller.dto.RegisterBookDTO;
import com.github.dev_jakki.library_api.controller.dto.ResultSearchBookDTO;
import com.github.dev_jakki.library_api.controller.mappers.LivroMapper;
import com.github.dev_jakki.library_api.model.GeneroLivro;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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
    public ResponseEntity<Page<ResultSearchBookDTO>> pesquisar(
            @RequestParam(name = "isbn", required = false)
            String isbn,
            @RequestParam(name = "titulo", required = false)
            String titulo,
            @RequestParam(name = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(name = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(name = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(name = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(name = "regs-pagina", defaultValue = "10")
            Integer regsPagina
    ) {
        Page<Livro> paginaResult = service.pesquisar(isbn, titulo, anoPublicacao, genero, nomeAutor, pagina, regsPagina);

        Page<ResultSearchBookDTO> livrosPaginados = paginaResult.map(mapper::toDTO);

        return ResponseEntity.ok(livrosPaginados);
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
