package com.proyectograduacion.PGwebONG.domain.personas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {


    boolean existsByDpi(String dpi);

    Page<Persona> findByActivoTrue(Pageable pageable);

//    Page<Persona> findByDpiAndActivoTrue(String dpi);

    Persona getReferenceByDpi(String dpi);
}
