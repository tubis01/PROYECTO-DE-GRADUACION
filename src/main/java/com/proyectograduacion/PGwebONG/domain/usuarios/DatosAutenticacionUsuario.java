package com.proyectograduacion.PGwebONG.domain.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank
        String usuario,
        @NotBlank
        String contrasena
) {
}
