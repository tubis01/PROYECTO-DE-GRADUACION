package com.proyectograduacion.PGwebONG.domain.beneficiario.validaciones;

import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosregistroBeneficiario;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;


@Component
public class PersonaAsignadaAlProyecto implements IValidadorBeneficiario{

    private final BeneficiarioRepository beneficiarioRepository;

    public PersonaAsignadaAlProyecto(BeneficiarioRepository beneficiarioRepository){
        this.beneficiarioRepository = beneficiarioRepository;
    }

    @Override
    public void validar(DatosregistroBeneficiario datosregistroBeneficiario) {

        Boolean PersonaYaAsignada = beneficiarioRepository.existsByPersonaAndProyecto(datosregistroBeneficiario.dpi(), datosregistroBeneficiario.proyecto());

        if(PersonaYaAsignada){
            throw new ValidationException("La persona ya esta asignada a este proyecto");
        }
    }
}
