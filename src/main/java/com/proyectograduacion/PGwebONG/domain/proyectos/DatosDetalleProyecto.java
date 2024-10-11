package com.proyectograduacion.PGwebONG.domain.proyectos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;

public record DatosDetalleProyecto(
        Long id,
        String nombre,
        String descripcion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        String fechaInicio,
        Estado estado,
        String fechaFin
) {

        public DatosDetalleProyecto(Proyecto proyecto) {
            this(
                    proyecto.getId(),
                    proyecto.getNombre(),
                    proyecto.getDescripcion(),
                    proyecto.getFechaInicio().toString(),
                    proyecto.getEstado(),
                    proyecto.getFechaFin().toString()
            );

        }

}
