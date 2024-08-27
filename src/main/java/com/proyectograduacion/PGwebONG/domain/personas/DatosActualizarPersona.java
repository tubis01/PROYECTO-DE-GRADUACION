package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.DatosDireccion;
import com.proyectograduacion.PGwebONG.domain.direccion.Direccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.DatosDiscapacidad;
import com.proyectograduacion.PGwebONG.domain.discapacidad.Discapacidad;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosActualizarPersona(
        @NotBlank
        String dpi,
        String NIT,
        String primerNombre,
        String segundoNombre,
        String tercerNombre,
        String primerApellido,
        String segundoApellido,
        String telefono,
        LocalDateTime fechaNacimiento,
        String etnia,
        String genero,
        String estadoCivil,
        Integer numeroHijos,
        DatosDireccion direccion,
        DatosDiscapacidad discapacidad,
        String comunidadLinguistica,
        String area,
        String cultivo,
        boolean vendeExecedenteCosecha,
        String tipoProductor,
        String responsable,
        Organizacion organizacion,
        String tipoVivienda


) {
}
