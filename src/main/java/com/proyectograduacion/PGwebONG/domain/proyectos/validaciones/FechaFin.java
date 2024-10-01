package com.proyectograduacion.PGwebONG.domain.proyectos.validaciones;

import com.proyectograduacion.PGwebONG.domain.proyectos.DatosRegistroProyecto;
import org.springframework.stereotype.Component;

@Component
public class FechaFin implements ValidadorProyectos{

    @Override
    public void validar(DatosRegistroProyecto datosProyecto) {

        if (datosProyecto.fechaFin() == null) {
            return;
        }

        if (datosProyecto.fechaFin().isBefore(datosProyecto.fechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }
}
