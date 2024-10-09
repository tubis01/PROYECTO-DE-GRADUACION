package com.proyectograduacion.PGwebONG.domain.discapacidad;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Discapacidad {

    @Column(name = "discapacidad_auditiva")
    boolean discapacidadAuditiva;
    @Column(name = "discapacidad_motora")
    boolean discapacidadMotora;
    @Column(name = "discapacidad_intelectual")
    boolean dicapacidadIntelectual;

    public Discapacidad(DatosDiscapacidad discapacidad) {
        this.discapacidadAuditiva = discapacidad.discapacidadAuditiva();
        this.discapacidadMotora = discapacidad.discapacidadMotora();
        this.dicapacidadIntelectual = discapacidad.discapacidadIntelectual();
    }
}