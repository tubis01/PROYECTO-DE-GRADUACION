package com.proyectograduacion.PGwebONG.domain.responsables;

public record DatosDetalleResponsable(
        Long id,
        String nombre,
        String apellido,
        String genero,
        String fechaNacimiento,
        String correo,
        String telefono
) {
    public DatosDetalleResponsable (Responsable responsable){
        this(responsable.getId(),
                responsable.getNombre(),
            responsable.getApellido(),
            responsable.getGenero().toString(),
            responsable.getFechaNacimiento().toString(),
            responsable.getCorreo(),
            responsable.getTelefono());
    }
}
