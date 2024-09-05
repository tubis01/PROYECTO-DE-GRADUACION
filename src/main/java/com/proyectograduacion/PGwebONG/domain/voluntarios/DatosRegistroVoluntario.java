package com.proyectograduacion.PGwebONG.domain.voluntarios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyectograduacion.PGwebONG.domain.personas.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroVoluntario(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotBlank
                @Email
        String correo,
        @NotBlank
        String telefono,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate fechaNacimiento,
        @NotNull
        Genero genero,
        String comentarios
) {
}
