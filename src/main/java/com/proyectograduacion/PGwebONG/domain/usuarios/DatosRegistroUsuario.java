package com.proyectograduacion.PGwebONG.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record DatosRegistroUsuario(

        @Email
        String email,
        @NotBlank
        String usuario,
        @NotBlank
        String clave,
        Set<String> rol
) {
}
