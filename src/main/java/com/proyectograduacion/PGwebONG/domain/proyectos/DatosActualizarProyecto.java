package com.proyectograduacion.PGwebONG.domain.proyectos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosActualizarProyecto(
        @NotNull
        Long id,
        String nombre,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {
}
