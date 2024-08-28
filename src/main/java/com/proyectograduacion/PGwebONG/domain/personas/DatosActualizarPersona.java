package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.DatosDireccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.DatosDiscapacidad;
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
        Genero genero,
        String estadoCivil,
        Integer numeroHijos,
        DatosDireccion direccion,
        DatosDiscapacidad discapacidad,
        String comunidadLinguistica,
        String area,
        String cultivo,
        boolean vendeExecedenteCosecha,
        TipoProductor tipoProductor,
        String responsable,
        Organizacion organizacion,
        String tipoVivienda


) {
}