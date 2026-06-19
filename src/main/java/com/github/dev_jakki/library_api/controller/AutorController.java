package com.github.dev_jakki.library_api.controller;

import com.github.dev_jakki.library_api.controller.dto.AutorDTO;
import com.github.dev_jakki.library_api.controller.dto.ResponseError;
import com.github.dev_jakki.library_api.exceptions.OperationNotAllowedException;
import com.github.dev_jakki.library_api.exceptions.RegisterDuplicateException;
import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping // ou @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor) {
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            // return new ResponseEntity("Autor salvo com sucesso! " + autor, HttpStatus.CREATED);
            return ResponseEntity.created(location).build();
        } catch (RegisterDuplicateException e) {
            var erroDTO = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterPorId(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();

            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    // Método indempotente - independente de ter ID ou não, retorna sucesso
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        try {
            var idAutor = UUID.fromString(id);

            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e) {
            var erroResposta = ResponseError.responseDefault(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> resultado = service.pesquisar(nome, nacionalidade);
        List<AutorDTO> autores = resultado
                .stream()
                .map(autor -> new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade())
        ).collect(Collectors.toList());

        return ResponseEntity.ok(autores);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto) {
        try {
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
        } catch (RegisterDuplicateException e) {
            var erroDTO = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
