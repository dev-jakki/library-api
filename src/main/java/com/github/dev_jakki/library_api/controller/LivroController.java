package com.github.dev_jakki.library_api.controller;

import com.github.dev_jakki.library_api.controller.dto.RegisterBookDTO;
import com.github.dev_jakki.library_api.controller.dto.ResultSearchBookDTO;
import com.github.dev_jakki.library_api.controller.mappers.LivroMapper;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
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
}
