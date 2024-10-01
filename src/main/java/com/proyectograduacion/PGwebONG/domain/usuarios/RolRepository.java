package com.proyectograduacion.PGwebONG.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Rol, Integer> {

    Rol findByNombre(RolNombre rolNombre);
}
