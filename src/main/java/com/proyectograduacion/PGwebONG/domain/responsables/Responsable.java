package com.proyectograduacion.PGwebONG.domain.responsables;

import com.proyectograduacion.PGwebONG.domain.common.PersonaBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Responsable")
@Table(name = "responsable")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Responsable  extends PersonaBase {




}
