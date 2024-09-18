package com.proyectograduacion.PGwebONG.domain.beneficiario.validaciones;

import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosregistroBeneficiario;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ProyectoActivo implements IValidadorBeneficiario{

    private final ProyectoRepository proyectoRepository;

    public ProyectoActivo(ProyectoRepository proyectoRepository){
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public void validar(DatosregistroBeneficiario datosregistroBeneficiario) {
        if (datosregistroBeneficiario.proyecto() == null) {
            return;
        }

        Boolean proyectoActivo =  proyectoRepository.findActivoById(datosregistroBeneficiario.proyecto());

        if (!proyectoActivo) {
            throw new ValidationException("No se puede permitir registrar beneficiarios en proyectos finalizados");
        }


    }
}
