package com.proyectograduacion.PGwebONG.domain.proyectos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroProyecto
        (
                @NotBlank
                String nombre,
                @NotBlank
                String descripcion,
                @NotNull
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                LocalDate fechaInicio,
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                LocalDate fechaFin
        ) {
}
