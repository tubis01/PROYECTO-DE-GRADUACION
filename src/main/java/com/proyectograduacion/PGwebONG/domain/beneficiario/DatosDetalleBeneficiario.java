package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.proyectograduacion.PGwebONG.domain.direccion.Direccion;
import com.proyectograduacion.PGwebONG.domain.discapacidad.Discapacidad;

public record DatosDetalleBeneficiario(

        Long id,
        String dpi,
        String primerNombre,
        String segundoNombre,
        String primerApellido,
        String segundoApellido,
        String telefono,
        String fechaNacimiento,
        String etnia,
        String genero,
        String estadoCivil,
        String numeroHijos,
        Direccion direccion,
        Discapacidad discapacidad,
        String comunidadLinguistica,
        String area,
        String cultivo,
        boolean vendeExecedenteCosecha,
        String tipoProductor,
        String responsable,
        String organizacion,
        String proyecto
) {
    public DatosDetalleBeneficiario (Beneficiario beneficiario){

        this(
                beneficiario.getId(),
                beneficiario.getPersona().getDpi(),
                beneficiario.getPersona().getPrimerNombre(),
                beneficiario.getPersona().getSegundoNombre(),
                beneficiario.getPersona().getPrimerApellido(),
                beneficiario.getPersona().getSegundoApellido(),
                beneficiario.getPersona().getTelefono(),
                beneficiario.getPersona().getFechaNacimiento().toString(),
                beneficiario.getPersona().getEtnia(),
                beneficiario.getPersona().getGenero().toString(),
                beneficiario.getPersona().getEstadoCivil(),
                beneficiario.getPersona().getNumeroHijos().toString(),
                beneficiario.getPersona().getDireccion(),
                beneficiario.getPersona().getDiscapacidad(),
                beneficiario.getPersona().getComunidadLinguistica(),
                beneficiario.getPersona().getArea(),
                beneficiario.getPersona().getCultivo(),
                beneficiario.getPersona().isVendeExecedenteCosecha(),
                beneficiario.getPersona().getTipoProductor().toString(),
                beneficiario.getPersona().getResponsable().getNombre(),
                beneficiario.getPersona().getOrganizacion().toString(),
                beneficiario.getProyecto().getNombre()


        );

    }
}
