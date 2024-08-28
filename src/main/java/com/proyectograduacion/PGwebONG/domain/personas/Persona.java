package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.Direccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.Discapacidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity(name = "Persona")
@Table(name = "personas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;

    @Column(unique = true)
    private String dpi;

    private String primerNombre;
    private String segundoNombre;
    private String tercerNombre;
    private String primerApellido;
    private String segundoApellido;

    @Column(unique = true)
    private String NIT;

    @Column(unique = true)
    private String telefono;

    private LocalDateTime fechaNacimiento;
    private String etnia;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String estadoCivil;
    private Integer numeroHijos;
    private String tipoVivienda;

    @Embedded
    private Direccion direccion;

    @Embedded
    private Discapacidad discapacidad;

    private String comunidadLinguistica;
    private String area;
    private String cultivo;

    @Column(name = "vende_excedente_cosecha")
    private boolean vendeExecedenteCosecha;


    @Enumerated(EnumType.STRING)
    private TipoProductor tipoProductor;

    private String responsable;

    @Enumerated(EnumType.STRING)
    private Organizacion organizacion;
    private boolean activo;


    // Constructor que usa DatosRegistroPersona
    public Persona(DatosRegistroPersona registroPersona) {
        this.dpi = registroPersona.dpi();
        this.primerNombre = registroPersona.primerNombre();
        this.segundoNombre = registroPersona.segundoNombre();
        this.tercerNombre = registroPersona.tercerNombre();
        this.primerApellido = registroPersona.primerApellido();
        this.segundoApellido = registroPersona.segundoApellido();
        this.NIT = registroPersona.NIT();
        this.telefono = registroPersona.telefono();
        this.fechaNacimiento = registroPersona.fechaNacimiento();
        this.etnia = registroPersona.etnia();
        this.genero = registroPersona.genero();
        this.estadoCivil = registroPersona.estadoCivil();
        this.numeroHijos = registroPersona.numeroHijos();
        this.tipoVivienda = registroPersona.tipoVivienda();
        this.direccion = new Direccion(registroPersona.direccion());
        this.discapacidad = new Discapacidad(registroPersona.discapacidad());
        this.comunidadLinguistica = registroPersona.comunidadLinguistica();
        this.area = registroPersona.area();
        this.cultivo = registroPersona.cultivo();
        this.vendeExecedenteCosecha = registroPersona.vendeExecedenteCosecha();
        this.tipoProductor = registroPersona.tipoProductor();
        this.responsable = registroPersona.responsable();
        this.organizacion = registroPersona.organizacion();
        this.activo = true;
    }

    // Método para actualizar la Persona
    public void actualizarPersona(DatosActualizarPersona datosActualizarPersona) {
        if(datosActualizarPersona.NIT() != null) {
            this.NIT = datosActualizarPersona.NIT();
        }
        if(datosActualizarPersona.primerNombre() != null) {
            this.primerNombre = datosActualizarPersona.primerNombre();
        }
        if(datosActualizarPersona.segundoNombre() != null) {
            this.segundoNombre = datosActualizarPersona.segundoNombre();
        }
        if(datosActualizarPersona.tercerNombre() != null) {
            this.tercerNombre = datosActualizarPersona.tercerNombre();
        }
        if(datosActualizarPersona.primerApellido() != null) {
            this.primerApellido = datosActualizarPersona.primerApellido();
        }
        if(datosActualizarPersona.segundoApellido() != null) {
            this.segundoApellido = datosActualizarPersona.segundoApellido();
        }
        if(datosActualizarPersona.telefono() != null) {
            this.telefono = datosActualizarPersona.telefono();
        }
        if(datosActualizarPersona.fechaNacimiento() != null) {
            this.fechaNacimiento = datosActualizarPersona.fechaNacimiento();
        }
        if(datosActualizarPersona.etnia() != null) {
            this.etnia = datosActualizarPersona.etnia();
        }
        if(datosActualizarPersona.genero() != null) {
            this.genero = datosActualizarPersona.genero();
        }
        if(datosActualizarPersona.estadoCivil() != null) {
            this.estadoCivil = datosActualizarPersona.estadoCivil();
        }
        if(datosActualizarPersona.numeroHijos() != null) {
            this.numeroHijos = datosActualizarPersona.numeroHijos();
        }
        if(datosActualizarPersona.direccion() != null) {
            this.direccion = new Direccion(datosActualizarPersona.direccion());
        }
        if(datosActualizarPersona.discapacidad() != null) {
            this.discapacidad = new Discapacidad(datosActualizarPersona.discapacidad());
        }
        if(datosActualizarPersona.comunidadLinguistica() != null) {
            this.comunidadLinguistica = datosActualizarPersona.comunidadLinguistica();
        }
        if(datosActualizarPersona.area() != null) {
            this.area = datosActualizarPersona.area();
        }
        if(datosActualizarPersona.cultivo() != null) {
            this.cultivo = datosActualizarPersona.cultivo();
        }
        if(datosActualizarPersona.vendeExecedenteCosecha()) {
            this.vendeExecedenteCosecha = true;
        }
        if(datosActualizarPersona.tipoProductor() != null) {
            this.tipoProductor = datosActualizarPersona.tipoProductor();
        }

        if(datosActualizarPersona.responsable() != null) {
            this.responsable = datosActualizarPersona.responsable();
        }

        if(datosActualizarPersona.organizacion() != null) {
            this.organizacion = datosActualizarPersona.organizacion();
        }

        if(datosActualizarPersona.tipoVivienda() != null) {
            this.tipoVivienda = datosActualizarPersona.tipoVivienda();
        }



    }

    // Métodos para cambiar el estado de la persona
    public void eliminarPersona() {
        this.activo = false;
    }

    public void activarPersona() {
        this.activo = true;
    }
}
