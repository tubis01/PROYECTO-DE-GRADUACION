package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.service.ExportarAExcelService;
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
public class ExportExcelController {

    private final ExportarAExcelService exportarAExcelService;

    public ExportExcelController(ExportarAExcelService exportarAExcelService) {
        this.exportarAExcelService = exportarAExcelService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/benenficiarios")
    public ResponseEntity<byte[]> exportBeneficiariosAExcel(@RequestParam Long idProyecto,
                                                            @RequestParam boolean activo) throws IOException {
        byte[] excelBytes = exportarAExcelService.exportarBeneficiarioAExcel(idProyecto, activo);
        String nombreProyecto = exportarAExcelService.obtenerNombreProyecto(idProyecto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "beneficiarios_" + nombreProyecto + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

}

