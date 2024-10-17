package com.proyectograduacion.PGwebONG.domain.beneficiario;

public record DatosDetalleBeneficiario(

        Long id,
        String DPI,
        String primerNombre,
        String primerApellido,
        String segundoApellido,
        String telefono,
        String responsable,
        String organizacion,
        Long idProyecto,
        String NombreProyecto
) {
    public DatosDetalleBeneficiario (Beneficiario beneficiario){

        this(
                beneficiario.getId(),
                beneficiario.getPersona().getDpi(),
                beneficiario.getPersona().getPrimerNombre(),
                beneficiario.getPersona().getPrimerApellido(),
                beneficiario.getPersona().getSegundoApellido(),
                beneficiario.getPersona().getTelefono(),
                beneficiario.getPersona().getResponsable().getNombre(),
                beneficiario.getPersona().getOrganizacion().toString(),
                beneficiario.getProyecto().getId(),
                beneficiario.getProyecto().getNombre()

        );

    }
}
