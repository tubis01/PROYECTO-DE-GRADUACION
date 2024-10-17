package com.proyectograduacion.PGwebONG.domain.usuarios;

public record DatosDetalleUsuario(
        Long id,
        String email,
        String usuario,
        RolNombre rol
) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getUsuario(),
                usuario.getRoles() != null && !usuario.getRoles().isEmpty() ?
                        usuario.getRoles().iterator().next().getNombre() :
                        null //
        );
    }
}
