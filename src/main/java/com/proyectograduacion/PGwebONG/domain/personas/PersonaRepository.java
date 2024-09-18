package com.proyectograduacion.PGwebONG.domain.personas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PersonaRepository extends JpaRepository<Persona, Long> {


    boolean existsByDpi(String dpi);
    boolean existsByNIT(String nit);
    boolean existsByTelefono(String telefono);

    Page<Persona> findByActivoTrue(Pageable pageable);

    Persona getReferenceByDpi(String dpi);

    Page<Persona> findByActivoFalse(Pageable pageable);


    @Query("select p.activo from Persona  p where p.dpi = :dpiPersona")
    Boolean findActivoByDpi(String dpiPersona);
}
