package com.github.dev_jakki.library_api.controller;

import com.github.dev_jakki.library_api.controller.dto.RegisterBookDTO;
import com.github.dev_jakki.library_api.controller.mappers.LivroMapper;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
