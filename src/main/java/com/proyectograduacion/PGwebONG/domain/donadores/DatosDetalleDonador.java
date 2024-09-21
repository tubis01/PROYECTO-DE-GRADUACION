package com.proyectograduacion.PGwebONG.domain.donadores;

import com.proyectograduacion.PGwebONG.domain.personas.Genero;

import java.time.LocalDate;

public record DatosDetalleDonador(
        Long id,
        String nombre,
        String apellido,
        Genero genero,
        String correo,
        String telefono,
        String comentarios,
        LocalDate fechaNacimiento
) {


    public DatosDetalleDonador(Donador donador) {
        this(
                donador.getId(),
                donador.getNombre(),
                donador.getApellido(),
                donador.getGenero(),
                donador.getCorreo(),
                donador.getTelefono(),
                donador.getComentarios(),
                donador.getFechaNacimiento()
        );

    }
}
