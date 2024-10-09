package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.CodigoUbicaciones;
import com.proyectograduacion.PGwebONG.domain.direccion.DatosDireccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.DatosDiscapacidad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroPersona(
        @NotBlank
        String DPI,

        String NIT,

        @NotBlank
        String primerNombre,

        String segundoNombre,

        String tercerNombre,

        @NotBlank
        String primerApellido,

        String segundoApellido,

        @NotBlank
        String telefono,
        @NotNull
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate fechaNacimiento,
        @NotBlank
        String etnia,
        @NotNull
                @Valid
        Genero genero,
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
        boolean vendeExcedenteCosecha,
        @NotNull
        TipoProductor tipoProductor,
        @NotNull
        Long responsable,
        @NotNull
        Organizacion organizacion,
        @NotBlank
        String tipoVivienda
) {
    public CodigoUbicaciones obtenerCodigoUbicacion() {
        return direccion.obtenerCodigoUbicacion();
    }
}
