package com.proyectograduacion.PGwebONG.infra.security;

import java.util.List;

public record DatosJWTToken(
        String token,
        String userName,
        List<String> roles
) {
}
