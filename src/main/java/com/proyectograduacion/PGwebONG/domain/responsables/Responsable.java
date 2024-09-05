package com.proyectograduacion.PGwebONG.domain.responsables;

import com.proyectograduacion.PGwebONG.domain.common.PersonaBase;
import com.proyectograduacion.PGwebONG.domain.personas.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Responsable")
@Table(name = "responsables")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Responsable  extends PersonaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Persona> personas = new ArrayList<>();

    public Responsable(DatosRegistroResponsable datosResponsable){
        super();
        this.setNombre(datosResponsable.nombre());
        this.setApellido(datosResponsable.apellido());
        this.setGenero(datosResponsable.genero());
        this.setFechaNacimiento(datosResponsable.fechaNacimiento());
        this.setCorreo(datosResponsable.correo());
        this.setTelefono(datosResponsable.telefono());
        this.setActivo(true);
    }

    public void actualizarResponsable(DatosActualizarResponsable datosActualizarResponsable) {
        super.actualizarDatos(
                datosActualizarResponsable.nombre(),
                datosActualizarResponsable.apellido(),
                datosActualizarResponsable.genero(),
                datosActualizarResponsable.fechaNacimiento(),
                datosActualizarResponsable.correo(),
                datosActualizarResponsable.telefono()
        );

    }

    public void agregarPersona(Persona persona){
        persona.setResponsable(this);
        this.personas.add(persona);
    }


}
