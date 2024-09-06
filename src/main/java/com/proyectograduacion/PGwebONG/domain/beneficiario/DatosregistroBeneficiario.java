package com.proyectograduacion.PGwebONG.domain.beneficiario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosregistroBeneficiario(
        @NotBlank
        String dpi,
        @NotNull
        Long proyecto
) {
}
