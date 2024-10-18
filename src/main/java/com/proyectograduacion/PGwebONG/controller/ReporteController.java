package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.proyectos.DatosDetalleProyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.Estado;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.service.ReporteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/reporte")
@SecurityRequirement(name = "bearer-key")
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

//    @GetMapping("/beneficiariosActivos")
//    public ResponseEntity<Long> reporteBeneficiariosActivos(@RequestParam boolean activo) {
//        return ResponseEntity.ok(reporteService.contarBeneficiariosActivos(activo));
//    }

    @GetMapping("/beneficiariosPorMes")
    public ResponseEntity<Long> reporteBeneficiariosPorMes(@RequestParam int mes) {
        return ResponseEntity.ok(reporteService.contarBeneficiariosPorMes(mes));
    }

    @GetMapping("/ultimos-proyectos-finalizados")
    public ResponseEntity<List<DatosDetalleProyecto>> obtenerUltimosProyectosFinalizados() {
        List<Proyecto> proyectos = reporteService.obtenerUltimosProyectosFinalizados(5); // Limitar a 5 proyectos
        List<DatosDetalleProyecto> datosProyectos = proyectos.stream()
                .map(DatosDetalleProyecto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(datosProyectos);
    }

    @GetMapping("/totalBeneficiarios")
    public ResponseEntity<Long> reporteTotalBeneficiarios() {
        return ResponseEntity.ok(reporteService.contarTotalBeneficiarios());
    }


}
