package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.Direccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.Discapacidad;
import com.proyectograduacion.PGwebONG.domain.organizacion.Organizacion;
import com.proyectograduacion.PGwebONG.domain.responsables.Responsable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity(name = "Persona")
@Table(name = "personas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona  {

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
    @Column(name = "apellidodecasada")
    private String apellidoCasada;

    @Column(unique = true)
    private String NIT;

    @Column(unique = true)
    private String telefono;

    private LocalDate fechaNacimiento;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_responsable")
    private Responsable responsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacion;

    private boolean activo;


    // Constructor que usa DatosRegistroPersona
// Constructor que usa DatosRegistroPersona
    public Persona(DatosRegistroPersona registroPersona, Responsable responsable,
                   Organizacion organizacion) {
        this.dpi = registroPersona.DPI();
        this.primerNombre = esValorValido(registroPersona.primerNombre()) ? registroPersona.primerNombre() : null;
        this.segundoNombre = esValorValido(registroPersona.segundoNombre()) ? registroPersona.segundoNombre() : null;
        this.tercerNombre = esValorValido(registroPersona.tercerNombre()) ? registroPersona.tercerNombre() : null;
        this.primerApellido = esValorValido(registroPersona.primerApellido()) ? registroPersona.primerApellido() : null;
        this.segundoApellido = esValorValido(registroPersona.segundoApellido()) ? registroPersona.segundoApellido() : null;
        this.apellidoCasada = esValorValido(registroPersona.apellidoCasada()) ? registroPersona.apellidoCasada() : null;
        this.NIT = esValorValido(registroPersona.NIT()) ? registroPersona.NIT() : null;
        this.telefono = esValorValido(registroPersona.telefono()) ? registroPersona.telefono() : null;
        this.fechaNacimiento = registroPersona.fechaNacimiento();
        this.etnia = esValorValido(registroPersona.etnia()) ? registroPersona.etnia() : null;
        this.genero = registroPersona.genero();
        this.estadoCivil = esValorValido(registroPersona.estadoCivil()) ? registroPersona.estadoCivil() : null;
        this.numeroHijos = registroPersona.numeroHijos();
        this.tipoVivienda = esValorValido(registroPersona.tipoVivienda()) ? registroPersona.tipoVivienda() : null;
        this.direccion = registroPersona.direccion() != null ? new Direccion(registroPersona.direccion()) : null;
        this.discapacidad = registroPersona.discapacidad() != null ? new Discapacidad(registroPersona.discapacidad()) : null;
        this.comunidadLinguistica = esValorValido(registroPersona.comunidadLinguistica()) ? registroPersona.comunidadLinguistica() : null;
        this.area = esValorValido(registroPersona.area()) ? registroPersona.area() : null;
        this.cultivo = esValorValido(registroPersona.cultivo()) ? registroPersona.cultivo() : null;
        this.vendeExecedenteCosecha = registroPersona.vendeExcedenteCosecha();
        this.tipoProductor = registroPersona.tipoProductor();
        this.responsable = responsable;
        this.organizacion = organizacion;
        this.activo = true;
    }

    // Método auxiliar para validar si un valor es válido (no nulo y no vacío)
    private boolean esValorValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }


    // Método para actualizar la Persona
    // Método para actualizar la Persona
    public void actualizarPersona(DatosActualizarPersona datosActualizarPersona, Responsable responsable,
                                  Organizacion organizacion) {
        if(datosActualizarPersona.NIT() != null && !datosActualizarPersona.NIT().trim().isEmpty()) {
            this.NIT = datosActualizarPersona.NIT();
        } else if (datosActualizarPersona.NIT() != null) {
            this.NIT = null;
        }

        if(datosActualizarPersona.primerNombre() != null && !datosActualizarPersona.primerNombre().trim().isEmpty()) {
            this.primerNombre = datosActualizarPersona.primerNombre();
        } else if (datosActualizarPersona.primerNombre() != null) {
            this.primerNombre = null;
        }

        if(datosActualizarPersona.segundoNombre() != null && !datosActualizarPersona.segundoNombre().trim().isEmpty()) {
            this.segundoNombre = datosActualizarPersona.segundoNombre();
        } else if (datosActualizarPersona.segundoNombre() != null) {
            this.segundoNombre = null;
        }

        if(datosActualizarPersona.tercerNombre() != null && !datosActualizarPersona.tercerNombre().trim().isEmpty()) {
            this.tercerNombre = datosActualizarPersona.tercerNombre();
        } else if (datosActualizarPersona.tercerNombre() != null) {
            this.tercerNombre = null;
        }

        if(datosActualizarPersona.primerApellido() != null && !datosActualizarPersona.primerApellido().trim().isEmpty()) {
            this.primerApellido = datosActualizarPersona.primerApellido();
        } else if (datosActualizarPersona.primerApellido() != null) {
            this.primerApellido = null;
        }

        if(datosActualizarPersona.segundoApellido() != null && !datosActualizarPersona.segundoApellido().trim().isEmpty()) {
            this.segundoApellido = datosActualizarPersona.segundoApellido();
        } else if (datosActualizarPersona.segundoApellido() != null) {
            this.segundoApellido = null;
        }

        if(datosActualizarPersona.apellidoCasada() != null && !datosActualizarPersona.apellidoCasada().trim().isEmpty()) {
            this.apellidoCasada = datosActualizarPersona.apellidoCasada();
        } else if (datosActualizarPersona.apellidoCasada() != null) {
            this.apellidoCasada = null;
        }

        if(datosActualizarPersona.telefono() != null && !datosActualizarPersona.telefono().trim().isEmpty()) {
            this.telefono = datosActualizarPersona.telefono();
        } else if (datosActualizarPersona.telefono() != null) {
            this.telefono = null;
        }

        if(datosActualizarPersona.fechaNacimiento() != null) {
            this.fechaNacimiento = datosActualizarPersona.fechaNacimiento();
        }

        if(datosActualizarPersona.etnia() != null && !datosActualizarPersona.etnia().trim().isEmpty()) {
            this.etnia = datosActualizarPersona.etnia();
        } else if (datosActualizarPersona.etnia() != null) {
            this.etnia = null;
        }

        if(datosActualizarPersona.genero() != null) {
            this.genero = datosActualizarPersona.genero();
        }

        if(datosActualizarPersona.estadoCivil() != null && !datosActualizarPersona.estadoCivil().trim().isEmpty()) {
            this.estadoCivil = datosActualizarPersona.estadoCivil();
        } else if (datosActualizarPersona.estadoCivil() != null) {
            this.estadoCivil = null;
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

        if(datosActualizarPersona.comunidadLinguistica() != null && !datosActualizarPersona.comunidadLinguistica().trim().isEmpty()) {
            this.comunidadLinguistica = datosActualizarPersona.comunidadLinguistica();
        } else if (datosActualizarPersona.comunidadLinguistica() != null) {
            this.comunidadLinguistica = null;
        }

        if(datosActualizarPersona.area() != null && !datosActualizarPersona.area().trim().isEmpty()) {
            this.area = datosActualizarPersona.area();
        } else if (datosActualizarPersona.area() != null) {
            this.area = null;
        }

        if(datosActualizarPersona.cultivo() != null && !datosActualizarPersona.cultivo().trim().isEmpty()) {
            this.cultivo = datosActualizarPersona.cultivo();
        } else if (datosActualizarPersona.cultivo() != null) {
            this.cultivo = null;
        }

        if(datosActualizarPersona.vendeExecedenteCosecha()) {
            this.vendeExecedenteCosecha = true;
        }

        if(datosActualizarPersona.tipoProductor() != null) {
            this.tipoProductor = datosActualizarPersona.tipoProductor();
        }

        if(datosActualizarPersona.responsable() != null) {
            this.responsable = responsable;
        }

        if(datosActualizarPersona.organizacion() != null) {
            this.organizacion = organizacion;
        }

        if(datosActualizarPersona.tipoVivienda() != null && !datosActualizarPersona.tipoVivienda().trim().isEmpty()) {
            this.tipoVivienda = datosActualizarPersona.tipoVivienda();
        } else if (datosActualizarPersona.tipoVivienda() != null) {
            this.tipoVivienda = null;
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
