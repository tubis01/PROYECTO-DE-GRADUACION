package com.proyectograduacion.PGwebONG.domain.organizacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {
    Organizacion findByNombre(String nombre);
}
