package com.proyectograduacion.PGwebONG.domain.responsables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

    Page<Responsable> findByActivoTrue(Pageable pageable);

    Page<Responsable> findByActivoFalse(Pageable pageable);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);

}
