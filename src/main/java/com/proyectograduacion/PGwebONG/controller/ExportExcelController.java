package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.service.ExportResult;
import com.proyectograduacion.PGwebONG.service.ExportarAExcelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/export")
@SecurityRequirement(name = "bearer-key")
public class ExportExcelController {

    private final ExportarAExcelService exportarAExcelService;

    public ExportExcelController(ExportarAExcelService exportarAExcelService) {
        this.exportarAExcelService = exportarAExcelService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/beneficiarios")
    public ResponseEntity<byte[]> exportBeneficiariosAExcel(@RequestParam Long idProyecto,
                                                            @RequestParam boolean activo) throws IOException {
        ExportResult resultado = exportarAExcelService.exportarBeneficiarioAExcel(idProyecto, activo);
        return construirRespuestaExcel(resultado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/personas/responsable")
    public ResponseEntity<byte[]> exportarPersonasPorResponsable(@RequestParam Long idResponsable,
                                                                 @RequestParam boolean activo) throws IOException {
        ExportResult resultado = exportarAExcelService.exportaPersonaExel(idResponsable, activo);
        return construirRespuestaExcel(resultado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/personas/organizacion")
    public ResponseEntity<byte[]> exportarPersonasPorOrganizacion(@RequestParam Long idOrganizacion,
                                                                  @RequestParam boolean activo) throws IOException {
        ExportResult resultado = exportarAExcelService.exportaPersonaExelPorOrganizacion(idOrganizacion, activo);
        return construirRespuestaExcel(resultado);
    }

    // Método reutilizable para construir la respuesta HTTP con el archivo Excel
    private ResponseEntity<byte[]> construirRespuestaExcel(ExportResult resultado) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", resultado.getNombreArchivo());
        return ResponseEntity.ok()
                .headers(headers)
                .body(resultado.getContenido());
    }
}