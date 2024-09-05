package com.proyectograduacion.PGwebONG.domain.donadores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonadorRepository extends JpaRepository<Donador, Long> {

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);

    Page<Donador> findByActivoTrue(Pageable pageable);

    Page<Donador> findByActivoFalse(Pageable pageable);
}
