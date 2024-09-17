package com.proyectograduacion.PGwebONG.domain.usuarios;

import jakarta.validation.constraints.Email;

public record DatosRegistroUsuario(

        @Email
        String email,
        String usuario,
        String clave,
        RolNombre rol
) {
}
