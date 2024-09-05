package com.proyectograduacion.PGwebONG.domain.voluntarios;

import com.proyectograduacion.PGwebONG.domain.personas.Genero;

public record DatosDetalleVoluntario(
        Long id,
        String nombre,
        String apellido,
        Genero genero,
        String correo,
        String telefono,
        String fechaNacimiento,
        String comentarios

) {

    public DatosDetalleVoluntario(Voluntario voluntario) {
        this(
                voluntario.getId(),
                voluntario.getNombre(),
                voluntario.getApellido(),
                voluntario.getGenero(),
                voluntario.getCorreo(),
                voluntario.getTelefono(),
                voluntario.getFechaNacimiento().toString(),
                voluntario.getComentarios()
        );
    }
}
