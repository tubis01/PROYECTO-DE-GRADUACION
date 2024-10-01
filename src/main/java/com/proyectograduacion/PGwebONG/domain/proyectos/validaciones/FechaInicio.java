package com.proyectograduacion.PGwebONG.domain.proyectos.validaciones;

import com.proyectograduacion.PGwebONG.domain.proyectos.DatosRegistroProyecto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FechaInicio implements ValidadorProyectos{
    @Override
    public void validar(DatosRegistroProyecto datosProyecto) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = datosProyecto.fechaInicio();


        if (fechaInicio.isBefore(fechaActual)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser anterior a la fecha actual");
        }




    }
}
