package com.github.dev_jakki.library_api.validator;

import com.github.dev_jakki.library_api.exceptions.RegisterDuplicateException;
import com.github.dev_jakki.library_api.model.Autor;
import com.github.dev_jakki.library_api.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegisterDuplicateException("Autor já cadastrado!");
        }
    }

    public boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        /* Essa lógica funciona tanto para cadastro quanto para atualização,
           se o id do autor for nulo, então é cadastro, caso não, é atualização
         */
        if (autor.getId() == null) {
            return autorEncontrado.isPresent(); // retorna false, pois não existe o autor no banco ainda
        }

        // Para o caso de tentar atualizar o mesmo autor
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
