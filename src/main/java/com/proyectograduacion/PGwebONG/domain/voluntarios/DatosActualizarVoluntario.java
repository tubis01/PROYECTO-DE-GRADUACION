package com.proyectograduacion.PGwebONG.domain.voluntarios;

import com.proyectograduacion.PGwebONG.domain.personas.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosActualizarVoluntario(
        @NotNull
        Long id,
        String nombre,
        String apellido,
        Genero genero,
        @Email
        String correo,
        String telefono,
        LocalDate fechaNacimiento,
        String comentarios

) {
}
