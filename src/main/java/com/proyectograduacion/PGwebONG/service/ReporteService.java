package com.proyectograduacion.PGwebONG.service;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.Estado;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final ProyectoRepository proyectoRepository;

    public ReporteService(BeneficiarioRepository beneficiarioRepository, ProyectoRepository proyectoRepository) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public List<Beneficiario> generarReporteBeneficiariosPorProyecto(Long idProyecto, boolean activo) {
        return beneficiarioRepository.findByProyectoIdAndActivo(idProyecto, activo);
    }

    public List<Proyecto> generarReportePorEstado(Estado estado){
        return proyectoRepository.findByEstado(estado);
    }
}
