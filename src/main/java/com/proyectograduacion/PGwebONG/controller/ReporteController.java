package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.proyectos.Estado;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/beneficiarioProyeto")
    public ResponseEntity<List<Beneficiario>> reporteBeneficiariosPorProyecto(@RequestParam Long idProyecto, @RequestParam boolean activo) {
        return ResponseEntity.ok(reporteService.generarReporteBeneficiariosPorProyecto(idProyecto, activo));
    }

    @GetMapping("/proyectosPorEstado")
    public ResponseEntity<List<Proyecto>> reporteProyectosPorEstado(@RequestParam Estado estado) {
        return ResponseEntity.ok(reporteService.generarReportePorEstado(estado));
    }

}
