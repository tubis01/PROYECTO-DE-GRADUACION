package com.proyectograduacion.PGwebONG.domain.direccion;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Enumerated(EnumType.STRING)
    private CodigoUbicaciones codigoUbicacion;

    private String comunidad;

    public Direccion(DatosDireccion direccion) {
        if(direccion.codigo() != null) {
            this.codigoUbicacion = CodigoUbicaciones.fromCodigo(direccion.codigo());

        }else {
            throw new IllegalArgumentException("El código de ubicación no puede ser nulo");
        }
        this.comunidad = direccion.comunidad();

//        this.codigoUbicacion = CodigoUbicaciones.fromCodigo(direccion.codigo());
//        this.comunidad = direccion.comunidad();
    }

    // Métodos adicionales para obtener el departamento y municipio
    public String getCodigo() {
        return this.codigoUbicacion.getCodigo();
    }

    public String getCodigoDepartamento() {
        return this.codigoUbicacion.getCodigoDepartamento();
    }

    public String getNombreDepartamento() {
        return this.codigoUbicacion.getNombreDepartamento();
    }

    public String getCodigoMunicipio() {
        return this.codigoUbicacion.getCodigoMunicipio();
    }

    public String getNombreMunicipio() {
        return this.codigoUbicacion.getNombreMunicipio();
    }
}
