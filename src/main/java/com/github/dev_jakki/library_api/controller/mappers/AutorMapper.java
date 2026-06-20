package com.github.dev_jakki.library_api.controller.mappers;

import com.github.dev_jakki.library_api.controller.dto.AutorDTO;
import com.github.dev_jakki.library_api.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    // @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
