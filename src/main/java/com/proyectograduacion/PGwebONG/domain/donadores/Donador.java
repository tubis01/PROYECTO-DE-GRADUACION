package com.proyectograduacion.PGwebONG.domain.donadores;

import com.proyectograduacion.PGwebONG.domain.common.PersonaBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Donador")
@Table(name = "donadores")
public class Donador extends PersonaBase {

    public Donador(DatosRegistroDonador registroDonador){
        super();
        this.setNombre(registroDonador.nombre());
        this.setApellido(registroDonador.apellido());
        this.setGenero(registroDonador.genero());
        this.setFechaNacimiento(registroDonador.fechaNacimiento());
        this.setCorreo(registroDonador.correo());
        this.setTelefono(registroDonador.telefono());
        this.setComentarios(registroDonador.comentarios());
        this.setActivo(true);
    }

    public Donador() {

    }

    public void actualizarDonador(DatosActualizarDonador datosActualizarDonador) {
        super.actualizarDatos(
                datosActualizarDonador.nombre(),
                datosActualizarDonador.apellido(),
                datosActualizarDonador.genero(),
                datosActualizarDonador.fechaNacimiento(),
                datosActualizarDonador.correo(),
                datosActualizarDonador.telefono(),
                datosActualizarDonador.comentarios()
        );
    }

}
