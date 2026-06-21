package com.github.dev_jakki.library_api.controller;

import com.github.dev_jakki.library_api.controller.dto.AutorDTO;
import com.github.dev_jakki.library_api.controller.mappers.AutorMapper;
import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.service.AutorService;
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
@RequestMapping("autores") // http://localhost:8080/autores
@RequiredArgsConstructor // Automatiza injeção no construtor (vem do Lombok)
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping // ou @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);

        URI url = gerarHeaderLocation(autor.getId());

        // return new ResponseEntity("Autor salvo com sucesso! " + autor, HttpStatus.CREATED);
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterPorId(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

//        Optional<Autor> autorOptional = service.obterPorId(idAutor);
//
//        if (autorOptional.isPresent()) {
//            Autor autor = autorOptional.get();
//
//            AutorDTO dto = new AutorDTO(
//                    autor.getId(),
//                    autor.getNome(),
//                    autor.getDataNascimento(),
//                    autor.getNacionalidade()
//            );
//
//            return ResponseEntity.ok(dto);
//        }
//
//        return ResponseEntity.notFound().build();
    }

    // Metodo indempotente independente de ter ID ou não, retorna sucesso
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> result = service.pesquisarByExample(nome, nacionalidade);
        List<AutorDTO> autores = result
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(autores);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
