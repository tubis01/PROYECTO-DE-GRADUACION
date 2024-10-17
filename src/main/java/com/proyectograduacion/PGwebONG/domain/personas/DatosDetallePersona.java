package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.direccion.Direccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.Discapacidad;

import java.time.LocalDate;

public record DatosDetallePersona(

        String DPI,
                                  String NIT,
                                  String primerNombre,
                                  String segundoNombre,
                                  String tercerNombre,
                                  String primerApellido,
                                  String segundoApellido,
                                  String telefono,
//                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                  LocalDate fechaNacimiento,
                                  String etnia,
                                  Genero genero,
                                  String estadoCivil,
                                  Integer numeroHijos,
                                  Direccion direccion,
                                  Discapacidad discapacidad,
                                  String comunidadLinguistica,
                                  String area,
                                  String cultivo,
                                  boolean vendeExecedenteCosecha,
                                  TipoProductor tipoProductor,
                                  Long idResponsable,
                                  String responsable,
                                  Long idOrganizacion,
                                  String organizacion,
                                  String tipoVivienda
) {
    public DatosDetallePersona(Persona persona) {
        this(
                persona.getDpi(),
                persona.getNIT(),
                persona.getPrimerNombre(),
                persona.getSegundoNombre(),
                persona.getTercerNombre(),
                persona.getPrimerApellido(),
                persona.getSegundoApellido(),
                persona.getTelefono(),
                persona.getFechaNacimiento(),
                persona.getEtnia(),
                persona.getGenero(),
                persona.getEstadoCivil(),
                persona.getNumeroHijos(),
                persona.getDireccion(),
                persona.getDiscapacidad(),
                persona.getComunidadLinguistica(),
                persona.getArea(),
                persona.getCultivo(),
                persona.isVendeExecedenteCosecha(),
                persona.getTipoProductor(),
                persona.getResponsable().getId(),
                persona.getResponsable().getNombre(),
                persona.getOrganizacion().getId(),
                persona.getOrganizacion().getNombre(),
                persona.getTipoVivienda()
        );
    }
}
