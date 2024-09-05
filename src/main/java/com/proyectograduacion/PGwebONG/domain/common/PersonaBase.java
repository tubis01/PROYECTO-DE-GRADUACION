package com.proyectograduacion.PGwebONG.domain.common;

import com.proyectograduacion.PGwebONG.domain.personas.Genero;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class  PersonaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(unique = true)
    private String correo;

    @Column(unique = true)
    private String telefono;

//    private String comentarios;

    private boolean activo;

    public void actualizarDatos(String nombre, String apellido, Genero genero, LocalDate fechaNacimiento, String correo, String telefono){
        if(nombre != null){
            this.nombre = nombre;
        }
        if(apellido != null){
            this.apellido = apellido;
        }
        if(genero != null){
            this.genero = genero;
        }
        if(fechaNacimiento != null){
            this.fechaNacimiento = fechaNacimiento;
        }
        if(correo != null){
            this.correo = correo;
        }
        if(telefono != null){
            this.telefono = telefono;
        }
//        if(comentarios != null){
//            this.comentarios = comentarios;
//        }
    }

    public void activar(){
        this.activo = true;
    }

    public void desactivar(){
        this.activo = false;
    }

}
