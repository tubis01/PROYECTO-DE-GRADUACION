package com.proyectograduacion.PGwebONG.service;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
import com.proyectograduacion.PGwebONG.domain.organizacion.Organizacion;
import com.proyectograduacion.PGwebONG.domain.organizacion.OrganizacionService;
import com.proyectograduacion.PGwebONG.domain.personas.Persona;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoService;
import com.proyectograduacion.PGwebONG.domain.responsables.Responsable;
import com.proyectograduacion.PGwebONG.domain.responsables.ResponsableService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ExportarAExcelService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final ProyectoService proyectoService;
    private final PersonaRepository personaRepository;
    private final ResponsableService responsableService;
    private final OrganizacionService organizacionService;

    public ExportarAExcelService(BeneficiarioRepository beneficiarioRepository, ProyectoService proyectoService,
                                 PersonaRepository personaRepository, ResponsableService responsableService,
                                 OrganizacionService organizacionService) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.proyectoService = proyectoService;
        this.personaRepository = personaRepository;
        this.responsableService = responsableService;
        this.organizacionService = organizacionService;
    }

    // Método específico para exportar beneficiarios por proyecto
    public ExportResult exportarBeneficiarioAExcel(Long idProyecto, boolean activo) throws IOException {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);
        List<Beneficiario> beneficiarios = beneficiarioRepository.findByProyectoIdAndActivo(idProyecto, activo);
        List<Persona> personas = beneficiarios.stream().map(Beneficiario::getPersona).toList();
        System.out.println("beneficiarios por proyecto: " + beneficiarios);
        byte[] contenido = exportarPersonasAExcel(personas, "Beneficiarios Proyecto " + proyecto.getNombre());
        return new ExportResult(contenido, "beneficiarios_" + proyecto.getNombre() + ".xlsx");
    }

    public ExportResult exportaPersonaExel(Long idResponsable, boolean activo) throws IOException {
        Responsable responsable = responsableService.obtenerResponsablePorId(idResponsable);
        List<Persona> personas = personaRepository.findByResponsableIdAAndActivo(idResponsable, activo);
        System.out.println("personas por responsable: " + personas);
        byte[] contenido = exportarPersonasAExcel(personas, "Personas Responsable " + responsable.getNombre());
        return new ExportResult(contenido, "personas_" + responsable.getNombre() + ".xlsx");
    }

    public ExportResult exportaPersonaExelPorOrganizacion(Long idOrganizacion, boolean activo) throws IOException {
        Organizacion organizacion = organizacionService.obtenerOrganizacionPorId(idOrganizacion);
        List<Persona> personas = personaRepository.findByOrganizacionId(idOrganizacion, activo);
        System.out.println("personas por organzacion: " + personas);
        byte[] contenido = exportarPersonasAExcel(personas, "Personas Organización " + organizacion.getNombre());
        return new ExportResult(contenido, "personas_" + organizacion.getNombre() + ".xlsx");
    }

    // Método genérico para exportar personas a Excel
    private byte[] exportarPersonasAExcel(List<Persona> personas, String nombreHoja) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        workbook.setCompressTempFiles(true);
        Sheet sheet = workbook.createSheet(nombreHoja);

        crearEncabezado(sheet);
        int rowNum = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Persona persona : personas) {
            Row row = sheet.createRow(rowNum++);
            llenarFilaConDatosDePersona(row, persona, formatter);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
        return outputStream.toByteArray();
    }

    private void llenarFilaConDatosDePersona(Row row, Persona persona, DateTimeFormatter formatter) {
        // Exportar DPI como número si es posible
        if (persona.getDpi() != null && !persona.getDpi().isEmpty()) {
            try {
                row.createCell(0).setCellValue(Long.parseLong(persona.getDpi()));
            } catch (NumberFormatException e) {
                row.createCell(0).setCellValue(persona.getDpi()); // Si no es un número, exportar como texto
            }
        } else {
            row.createCell(0).setCellValue("");
        }

        row.createCell(1).setCellValue(Optional.ofNullable(persona.getPrimerNombre()).orElse(""));
        row.createCell(2).setCellValue(Optional.ofNullable(persona.getSegundoNombre()).orElse(""));
        row.createCell(3).setCellValue(Optional.ofNullable(persona.getTercerNombre()).orElse(""));
        row.createCell(4).setCellValue(Optional.ofNullable(persona.getPrimerApellido()).orElse(""));
        row.createCell(5).setCellValue(Optional.ofNullable(persona.getSegundoApellido()).orElse(""));
        row.createCell(6).setCellValue(Optional.ofNullable(persona.getApellidoCasada()).orElse(""));

        // Exportar NIT como número si es posible
        if (persona.getNIT() != null && !persona.getNIT().isEmpty()) {
            try {
                row.createCell(7).setCellValue(Long.parseLong(persona.getNIT()));
            } catch (NumberFormatException e) {
                row.createCell(7).setCellValue(persona.getNIT()); // Si no es un número, exportar como texto
            }
        } else {
            row.createCell(7).setCellValue("");
        }

        // Exportar teléfono como número si es posible
        if (persona.getTelefono() != null && !persona.getTelefono().isEmpty()) {
            try {
                row.createCell(8).setCellValue(Long.parseLong(persona.getTelefono()));
            } catch (NumberFormatException e) {
                row.createCell(8).setCellValue(persona.getTelefono()); // Si no es un número, exportar como texto
            }
        } else {
            row.createCell(8).setCellValue("");
        }

        // Formatear la fecha de nacimiento si está presente
        if (persona.getFechaNacimiento() != null) {
            row.createCell(9).setCellValue(persona.getFechaNacimiento().format(formatter));
        } else {
            row.createCell(9).setCellValue("");
        }

        row.createCell(10).setCellValue(Optional.ofNullable(persona.getEtnia()).orElse(""));
        row.createCell(11).setCellValue(persona.getGenero() != null ? persona.getGenero().name() : "");
        row.createCell(12).setCellValue(Optional.ofNullable(persona.getEstadoCivil()).orElse(""));

        // Exportar número de hijos como número si está presente
        if (persona.getNumeroHijos() != null) {
            row.createCell(13).setCellValue(persona.getNumeroHijos());
        } else {
            row.createCell(13).setCellValue("");
        }

        row.createCell(14).setCellValue(Optional.ofNullable(persona.getTipoVivienda()).orElse(""));

        if (persona.getDireccion() != null) {
            // Exportar códigos de departamento y municipio como números si son válidos
            if (persona.getDireccion().getCodigoDepartamento() != null && !persona.getDireccion().getCodigoDepartamento().isEmpty()) {
                try {
                    row.createCell(15).setCellValue(Long.parseLong(persona.getDireccion().getCodigoDepartamento()));
                } catch (NumberFormatException e) {
                    row.createCell(15).setCellValue(persona.getDireccion().getCodigoDepartamento());
                }
            } else {
                row.createCell(15).setCellValue("");
            }

            row.createCell(16).setCellValue(Optional.ofNullable(persona.getDireccion().getNombreDepartamento()).orElse(""));

            if (persona.getDireccion().getCodigoMunicipio() != null && !persona.getDireccion().getCodigoMunicipio().isEmpty()) {
                try {
                    row.createCell(17).setCellValue(Long.parseLong(persona.getDireccion().getCodigoMunicipio()));
                } catch (NumberFormatException e) {
                    row.createCell(17).setCellValue(persona.getDireccion().getCodigoMunicipio());
                }
            } else {
                row.createCell(17).setCellValue("");
            }

            row.createCell(18).setCellValue(Optional.ofNullable(persona.getDireccion().getNombreMunicipio()).orElse(""));
            row.createCell(19).setCellValue(Optional.ofNullable(persona.getDireccion().getComunidad()).orElse(""));
        } else {
            // Si la dirección es nula, dejar celdas vacías
            row.createCell(15).setCellValue("");
            row.createCell(16).setCellValue("");
            row.createCell(17).setCellValue("");
            row.createCell(18).setCellValue("");
            row.createCell(19).setCellValue("");
        }

        if (persona.getDiscapacidad() != null) {
            row.createCell(20).setCellValue(persona.getDiscapacidad().isDiscapacidadAuditiva() ? "Sí" : "No");
            row.createCell(21).setCellValue(persona.getDiscapacidad().isDiscapacidadMotora() ? "Sí" : "No");
            row.createCell(22).setCellValue(persona.getDiscapacidad().isDicapacidadIntelectual() ? "Sí" : "No");
        } else {
            // Si la discapacidad es nula, dejar celdas vacías
            row.createCell(20).setCellValue("");
            row.createCell(21).setCellValue("");
            row.createCell(22).setCellValue("");
        }

        row.createCell(23).setCellValue(Optional.ofNullable(persona.getComunidadLinguistica()).orElse(""));
        row.createCell(24).setCellValue(Optional.ofNullable(persona.getArea()).orElse(""));
        row.createCell(25).setCellValue(Optional.ofNullable(persona.getCultivo()).orElse(""));
        row.createCell(26).setCellValue(persona.isVendeExecedenteCosecha() ? "Sí" : "No");
        row.createCell(27).setCellValue(persona.getTipoProductor() != null ? persona.getTipoProductor().name() : "");
        row.createCell(28).setCellValue(Optional.ofNullable(persona.getResponsable()).map(Responsable::getNombre).orElse(""));
        row.createCell(29).setCellValue(Optional.ofNullable(persona.getOrganizacion()).map(Object::toString).orElse(""));
    }

    private void crearEncabezado(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] columnas = {"Dpi", "Primer nombre", "Segundo nombre", "Tercer Nombre", "Primer apellido", "Segundo apellido",
                "Apellido de casada", "Nit", "Teléfono", "Fecha de nacimiento", "Etnia", "Genero", "Estado Civil", "Numero De Hijos",
                "Tipo Vivienda", "Código departamento", "Departamento", "Código municipio", "Municipio",
                "Comunidad", "Discapacidad auditiva", "Discapacidad motora", "Discapacidad intelectual",
                "Comunidad Lingüística", "Area", "Cultivo", "Vende Excedente cosecha", "Tipo productor",
                "Responsable", "Organización"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }
    }
}

