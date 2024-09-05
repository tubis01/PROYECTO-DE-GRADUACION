package com.proyectograduacion.PGwebONG.domain.voluntarios;


import com.proyectograduacion.PGwebONG.domain.common.PersonaBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity(name = "Voluntario")
@Table(name = "voluntarios")
public class Voluntario  extends PersonaBase {

    private String comentarios;

    public Voluntario(DatosRegistroVoluntario registroVoluntario){
        super();
        this.setNombre(registroVoluntario.nombre());
        this.setApellido(registroVoluntario.apellido());
        this.setGenero(registroVoluntario.genero());
        this.setFechaNacimiento(registroVoluntario.fechaNacimiento());
        this.setCorreo(registroVoluntario.correo());
        this.setTelefono(registroVoluntario.telefono());
        this.comentarios = registroVoluntario.comentarios();
        this.setActivo(true);
    }

    public Voluntario() {

    }
    public void actualizarVoluntario(DatosActualizarVoluntario datosActualizarVoluntario) {
        super.actualizarDatos(
                datosActualizarVoluntario.nombre(),
                datosActualizarVoluntario.apellido(),
                datosActualizarVoluntario.genero(),
                datosActualizarVoluntario.fechaNacimiento(),
                datosActualizarVoluntario.correo(),
                datosActualizarVoluntario.telefono()
//                datosActualizarVoluntario.comentarios()
        );
    }

}
