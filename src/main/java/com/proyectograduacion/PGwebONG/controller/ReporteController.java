package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.proyectos.Estado;
import com.proyectograduacion.PGwebONG.service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/reporte")
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/beneficiarioProyecto")
    public ResponseEntity<Long> contarBeneficiariosPorProyecto(@RequestParam Long idProyecto, @RequestParam boolean activo) {
        return ResponseEntity.ok(reporteService.contarBeneficiariosPorProyecto(idProyecto, activo));
    }

    @GetMapping("/proyectosPorEstado")
    public ResponseEntity<Long> contarProyectosPorEstado(@RequestParam Estado estado) {
        return ResponseEntity.ok(reporteService.contarProyectosPorEstado(estado));
    }

    @GetMapping("/beneficiariosActivos")
    public ResponseEntity<Long> reporteBeneficiariosActivos(@RequestParam boolean activo) {
        return ResponseEntity.ok(reporteService.contarBeneficiariosActivos(activo));
    }

    @GetMapping("/beneficiariosPorMes")
    public ResponseEntity<Long> reporteBeneficiariosPorMes(@RequestParam int mes) {
        return ResponseEntity.ok(reporteService.contarBeneficiariosPorMes(mes));
    }

}
