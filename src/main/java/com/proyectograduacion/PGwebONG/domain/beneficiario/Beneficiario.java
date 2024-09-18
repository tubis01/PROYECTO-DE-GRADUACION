package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.proyectograduacion.PGwebONG.domain.personas.Persona;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Beneficiario")
@Table(name = "beneficiarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_persona", "id_proyecto"})})
@Getter
@Setter
@NoArgsConstructor
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    private boolean activo;

    public Beneficiario(Persona persona, Proyecto proyecto){
        this.persona = persona;
        this.proyecto = proyecto;
        this.activo = true;
    }


    public void desactivar() {
        this.activo = false;
    }
}
