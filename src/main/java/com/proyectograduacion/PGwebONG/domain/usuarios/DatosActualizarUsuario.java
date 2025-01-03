package com.proyectograduacion.PGwebONG.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull
        Long id,
        @Email
        String email,
        String clave
) {
}
