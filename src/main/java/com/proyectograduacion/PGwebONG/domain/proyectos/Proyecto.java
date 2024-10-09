package com.proyectograduacion.PGwebONG.domain.proyectos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "Proyecto")
@Table(name = "proyectos")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;



    private boolean activo;

    public Proyecto(DatosRegistroProyecto datosRegistroProyecto) {
        {
            this.nombre = datosRegistroProyecto.nombre();
            this.descripcion = datosRegistroProyecto.descripcion();
            this.fechaInicio = datosRegistroProyecto.fechaInicio();
            this.fechaFin = datosRegistroProyecto.fechaFin();
            this.estado = Estado.En_proceso;
            this.activo = true;
        }
    }


    public void finalizarProyecto() {
        this.activo = false;
        this.estado = Estado.Finalizado;
    }

    public void actualizarProyecto(DatosActualizarProyecto actualizarProyecto) {

        if(actualizarProyecto.nombre() != null){
            this.nombre = actualizarProyecto.nombre();
        }
        if(actualizarProyecto.descripcion() != null){
            this.descripcion = actualizarProyecto.descripcion();
        }
        if(actualizarProyecto.fechaInicio() != null){
            this.fechaInicio = actualizarProyecto.fechaInicio();
        }
        if(actualizarProyecto.fechaFin() != null){
            this.fechaFin = actualizarProyecto.fechaFin();
        }

    }
}
