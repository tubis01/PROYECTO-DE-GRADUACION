package com.proyectograduacion.PGwebONG.domain.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    Page<Usuario> findByActivoTrue(Pageable pageable);

    Page<Usuario> findByActivoFalse(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByUsuario(String usuario);

    Usuario findByUsuarioOrEmail(String usuario, String email);
}
