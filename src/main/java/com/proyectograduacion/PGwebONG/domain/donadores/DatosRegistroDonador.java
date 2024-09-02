package com.proyectograduacion.PGwebONG.domain.donadores;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyectograduacion.PGwebONG.domain.personas.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroDonador(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotNull
        Genero genero,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate fechaNacimiento,
        @NotBlank
        @Email
        String correo,
        @NotBlank
        String telefono,
        String comentarios
) {
}
