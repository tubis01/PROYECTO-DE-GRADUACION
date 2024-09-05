package com.proyectograduacion.PGwebONG.domain.proyectos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Page<Proyecto> findByActivoTrue(Pageable pageable);

    Page<Proyecto> findByActivoFalse(Pageable pageable);
}
