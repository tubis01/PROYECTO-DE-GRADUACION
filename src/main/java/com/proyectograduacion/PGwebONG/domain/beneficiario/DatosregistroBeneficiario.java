package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosregistroBeneficiario(
        @NotBlank
        String dpi,
        @NotNull
        Long proyecto
) {
}
