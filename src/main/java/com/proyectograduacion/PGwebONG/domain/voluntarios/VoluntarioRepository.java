package com.proyectograduacion.PGwebONG.domain.voluntarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
    boolean existsById(Long id);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);

    Page<Voluntario> findByActivoFalse(Pageable pageable);

    Page<Voluntario> findByActivoTrue(Pageable pageable);
}
