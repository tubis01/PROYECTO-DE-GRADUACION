package com.proyectograduacion.PGwebONG.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
        @NotBlank
        String codigo,
        @NotBlank// Este campo se mantendrá para convertir el código en enum
        String comunidad
) {
    public CodigoUbicaciones obtenerCodigoUbicacion() {
        return CodigoUbicaciones.fromCodigo(codigo);
    }
}
