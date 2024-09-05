package com.proyectograduacion.PGwebONG.domain.responsables;

import com.proyectograduacion.PGwebONG.domain.personas.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosActualizarResponsable (
        @NotNull
        Long id,
        String nombre,
        String apellido,
        Genero genero,
        LocalDate fechaNacimiento,
        @Email
        String correo,
        String telefono

){
}
