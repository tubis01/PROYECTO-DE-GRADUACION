package com.proyectograduacion.PGwebONG.domain.donadores;

import com.proyectograduacion.PGwebONG.domain.common.PersonaBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Donador")
@Table(name = "donadores")
@NoArgsConstructor
@Getter
public class Donador extends PersonaBase {

    private String comentarios;

    public Donador(DatosRegistroDonador registroDonador){
        super();
        this.comentarios = registroDonador.comentarios();
        this.setNombre(registroDonador.nombre());
        this.setApellido(registroDonador.apellido());
        this.setGenero(registroDonador.genero());
        this.setFechaNacimiento(registroDonador.fechaNacimiento());
        this.setCorreo(registroDonador.correo());
        this.setTelefono(registroDonador.telefono());
        this.setActivo(true);
    }

    public void actualizarDonador(DatosActualizarDonador datosActualizarDonador) {
        super.actualizarDatos(
                datosActualizarDonador.nombre(),
                datosActualizarDonador.apellido(),
                datosActualizarDonador.genero(),
                datosActualizarDonador.fechaNacimiento(),
                datosActualizarDonador.correo(),
                datosActualizarDonador.telefono()
        );
    }

}
