package com.proyectograduacion.PGwebONG.service;

import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.Estado;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoRepository;
import org.springframework.stereotype.Service;


@Service
public class ReporteService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final ProyectoRepository proyectoRepository;

    public ReporteService(BeneficiarioRepository beneficiarioRepository, ProyectoRepository proyectoRepository) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public long contarBeneficiariosPorProyecto(Long idProyecto, boolean activo ){
        return beneficiarioRepository.countByProyectoIdAndActivo(idProyecto, activo);
    }

    public long contarProyectosPorEstado(Estado estado){
        return proyectoRepository.countByEstado(estado);
    }

    public long contarBeneficiariosActivos(boolean activo){
        return beneficiarioRepository.countByActivo(activo);
    }

    public long contarBeneficiariosPorMes(int mes){
        return beneficiarioRepository.countByMesDeAsignacion(mes);
    }




}
