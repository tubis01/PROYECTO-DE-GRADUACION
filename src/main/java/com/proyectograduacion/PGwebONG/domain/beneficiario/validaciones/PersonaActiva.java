package com.proyectograduacion.PGwebONG.domain.beneficiario.validaciones;

import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosregistroBeneficiario;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PersonaActiva implements IValidadorBeneficiario{

    private final PersonaRepository personaRepository;

    public PersonaActiva(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }

    @Override
    public void validar(DatosregistroBeneficiario datosregistroBeneficiario) {

        if (datosregistroBeneficiario.dpi() == null) {
            return;
        }

        Boolean personaActiva =  personaRepository.findActivoByDpi(datosregistroBeneficiario.dpi());

        if (!personaActiva) {
            throw new ValidationException("No se puede permitir registrar beneficiarios inactivos");
        }
    }
}
