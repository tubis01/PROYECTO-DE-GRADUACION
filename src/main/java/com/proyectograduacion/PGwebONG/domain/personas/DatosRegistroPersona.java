package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.CodigoUbicaciones;
import com.proyectograduacion.PGwebONG.domain.direccion.DatosDireccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.DatosDiscapacidad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroPersona(
        @NotBlank
        String dpi,
        @NotBlank
        String NIT,
        @NotBlank
        String primerNombre,
        @NotBlank
        String segundoNombre,

        String tercerNombre,
        @NotBlank
        String primerApellido,
        @NotBlank
        String segundoApellido,
        @NotBlank
        String telefono,
        @NotNull
        LocalDateTime fechaNacimiento,
        @NotBlank
        String etnia,
        @NotBlank
        String genero,
        @NotBlank
        String estadoCivil,
        @NotNull
        Integer numeroHijos,
        @Valid
        DatosDireccion direccion,
        @Valid
        DatosDiscapacidad discapacidad,
        @NotBlank
        String comunidadLinguistica,
        @NotBlank
        String area,
        @NotBlank
        String cultivo,
        @NotNull
        boolean vendeExecedenteCosecha,
        @NotBlank
        String tipoProductor,
        @NotBlank
        String responsable,
        @NotNull
        Organizacion organizacion,
        @NotBlank
        String tipoVivienda
) {
    public CodigoUbicaciones obtenerCodigoUbicacion() {
        return direccion.obtenerCodigoUbicacion();
    }
}
