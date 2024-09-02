package com.proyectograduacion.PGwebONG.domain.donadores;

import com.proyectograduacion.PGwebONG.domain.personas.Genero;

public record DatosDetalleDonador(
        Long id,
        String nombre,
        String apellido,
        Genero genero,
        String correo,
        String telefono,
        String comentarios
) {


    public DatosDetalleDonador(Donador donador) {
        this(
                donador.getId(),
                donador.getNombre(),
                donador.getApellido(),
                donador.getGenero(),
                donador.getCorreo(),
                donador.getTelefono(),
                donador.getComentarios()
        );

    }
}
