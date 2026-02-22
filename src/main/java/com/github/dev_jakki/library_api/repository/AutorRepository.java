package com.github.dev_jakki.library_api.repository;

import com.github.dev_jakki.library_api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> { }
