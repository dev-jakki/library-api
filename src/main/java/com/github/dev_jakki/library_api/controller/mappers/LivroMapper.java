package com.github.dev_jakki.library_api.controller.mappers;

import com.github.dev_jakki.library_api.controller.dto.RegisterBookDTO;
import com.github.dev_jakki.library_api.model.Livro;
import com.github.dev_jakki.library_api.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    public AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(RegisterBookDTO dto);

    public abstract RegisterBookDTO toDTO(Livro livro);

}
