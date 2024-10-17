package com.proyectograduacion.PGwebONG.service;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
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

    public ExportarAExcelService(BeneficiarioRepository beneficiarioRepository, ProyectoService proyectoService,
                                 PersonaRepository personaRepository, ResponsableService responsableService) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.proyectoService = proyectoService;
        this.personaRepository = personaRepository;
        this.responsableService = responsableService;
    }

    public byte[] exportarBeneficiarioAExcel(Long idProyecto, boolean activo) throws IOException {

        System.out.println("Exportando beneficiarios a excel");
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);

        List<Beneficiario> beneficiarios = beneficiarioRepository.findByProyectoIdAndActivo(idProyecto, activo);

//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Beneficiarios Proyecto " + proyecto.getNombre());
        SXSSFWorkbook workbook = new SXSSFWorkbook(); // Utiliza SXSSFWorkbook en lugar de XSSFWorkbook
        workbook.setCompressTempFiles(true); // Opcional: comprimir archivos temporales
        Sheet sheet = workbook.createSheet("Beneficiarios Proyecto " + proyecto.getNombre());

        crearEncabezado(sheet);


        int rowNum = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Beneficiario beneficiario : beneficiarios) {
            Row row = sheet.createRow(rowNum++);

            // Validación de campos opcionales y asignación de valores
            // Exportar DPI como número si es posible
            if (beneficiario.getPersona().getDpi() != null && !beneficiario.getPersona().getDpi().isEmpty()) {
                try {
                    row.createCell(0).setCellValue(Long.parseLong(beneficiario.getPersona().getDpi()));
                } catch (NumberFormatException e) {
                    row.createCell(0).setCellValue(beneficiario.getPersona().getDpi()); // Si no es un número, exportar como texto
                }
            } else {
                row.createCell(0).setCellValue("");
            }

            row.createCell(1).setCellValue(Optional.ofNullable(beneficiario.getPersona().getPrimerNombre()).orElse(""));
            row.createCell(2).setCellValue(Optional.ofNullable(beneficiario.getPersona().getSegundoNombre()).orElse(""));
            row.createCell(3).setCellValue(Optional.ofNullable(beneficiario.getPersona().getTercerNombre()).orElse(""));
            row.createCell(4).setCellValue(Optional.ofNullable(beneficiario.getPersona().getPrimerApellido()).orElse(""));
            row.createCell(5).setCellValue(Optional.ofNullable(beneficiario.getPersona().getSegundoApellido()).orElse(""));
            row.createCell(6).setCellValue(Optional.ofNullable(beneficiario.getPersona().getApellidoCasada()).orElse(""));

            // Exportar NIT como número si es posible
            if (beneficiario.getPersona().getNIT() != null && !beneficiario.getPersona().getNIT().isEmpty()) {
                try {
                    row.createCell(7).setCellValue(Long.parseLong(beneficiario.getPersona().getNIT()));
                } catch (NumberFormatException e) {
                    row.createCell(7).setCellValue(beneficiario.getPersona().getNIT()); // Si no es un número, exportar como texto
                }
            } else {
                row.createCell(7).setCellValue("");
            }

            // Exportar teléfono como número si es posible
            if (beneficiario.getPersona().getTelefono() != null && !beneficiario.getPersona().getTelefono().isEmpty()) {
                try {
                    row.createCell(8).setCellValue(Long.parseLong(beneficiario.getPersona().getTelefono()));
                } catch (NumberFormatException e) {
                    row.createCell(8).setCellValue(beneficiario.getPersona().getTelefono()); // Si no es un número, exportar como texto
                }
            } else {
                row.createCell(8).setCellValue("");
            }

            // Formatear la fecha de nacimiento si está presente
            if (beneficiario.getPersona().getFechaNacimiento() != null) {
                row.createCell(9).setCellValue(beneficiario.getPersona().getFechaNacimiento().format(formatter));
            } else {
                row.createCell(9).setCellValue("");
            }

            row.createCell(10).setCellValue(Optional.ofNullable(beneficiario.getPersona().getEtnia()).orElse(""));
            row.createCell(11).setCellValue(beneficiario.getPersona().getGenero() != null ? beneficiario.getPersona().getGenero().name() : "");
            row.createCell(12).setCellValue(Optional.ofNullable(beneficiario.getPersona().getEstadoCivil()).orElse(""));

            // Exportar número de hijos como número si está presente
            if (beneficiario.getPersona().getNumeroHijos() != null) {
                row.createCell(13).setCellValue(beneficiario.getPersona().getNumeroHijos());
            } else {
                row.createCell(13).setCellValue("");
            }

            row.createCell(14).setCellValue(Optional.ofNullable(beneficiario.getPersona().getTipoVivienda()).orElse(""));

            if (beneficiario.getPersona().getDireccion() != null) {
                // Exportar códigos de departamento y municipio como números si son válidos
                if (beneficiario.getPersona().getDireccion().getCodigoDepartamento() != null && !beneficiario.getPersona().getDireccion().getCodigoDepartamento().isEmpty()) {
                    try {
                        row.createCell(15).setCellValue(Long.parseLong(beneficiario.getPersona().getDireccion().getCodigoDepartamento()));
                    } catch (NumberFormatException e) {
                        row.createCell(15).setCellValue(beneficiario.getPersona().getDireccion().getCodigoDepartamento());
                    }
                } else {
                    row.createCell(15).setCellValue("");
                }

                row.createCell(16).setCellValue(Optional.ofNullable(beneficiario.getPersona().getDireccion().getNombreDepartamento()).orElse(""));

                if (beneficiario.getPersona().getDireccion().getCodigoMunicipio() != null && !beneficiario.getPersona().getDireccion().getCodigoMunicipio().isEmpty()) {
                    try {
                        row.createCell(17).setCellValue(Long.parseLong(beneficiario.getPersona().getDireccion().getCodigoMunicipio()));
                    } catch (NumberFormatException e) {
                        row.createCell(17).setCellValue(beneficiario.getPersona().getDireccion().getCodigoMunicipio());
                    }
                } else {
                    row.createCell(17).setCellValue("");
                }

                row.createCell(18).setCellValue(Optional.ofNullable(beneficiario.getPersona().getDireccion().getNombreMunicipio()).orElse(""));
                row.createCell(19).setCellValue(Optional.ofNullable(beneficiario.getPersona().getDireccion().getComunidad()).orElse(""));
            } else {
                // Si la dirección es nula, dejar celdas vacías
                row.createCell(15).setCellValue("");
                row.createCell(16).setCellValue("");
                row.createCell(17).setCellValue("");
                row.createCell(18).setCellValue("");
                row.createCell(19).setCellValue("");
            }

            if (beneficiario.getPersona().getDiscapacidad() != null) {
                row.createCell(20).setCellValue(beneficiario.getPersona().getDiscapacidad().isDiscapacidadAuditiva() ? "Sí" : "No");
                row.createCell(21).setCellValue(beneficiario.getPersona().getDiscapacidad().isDiscapacidadMotora() ? "Sí" : "No");
                row.createCell(22).setCellValue(beneficiario.getPersona().getDiscapacidad().isDicapacidadIntelectual() ? "Sí" : "No");
            } else {
                // Si la discapacidad es nula, dejar celdas vacías
                row.createCell(20).setCellValue("");
                row.createCell(21).setCellValue("");
                row.createCell(22).setCellValue("");
            }

            row.createCell(23).setCellValue(Optional.ofNullable(beneficiario.getPersona().getComunidadLinguistica()).orElse(""));
            row.createCell(24).setCellValue(Optional.ofNullable(beneficiario.getPersona().getArea()).orElse(""));
            row.createCell(25).setCellValue(Optional.ofNullable(beneficiario.getPersona().getCultivo()).orElse(""));
            row.createCell(26).setCellValue(beneficiario.getPersona().isVendeExecedenteCosecha() ? "Sí" : "No");
            row.createCell(27).setCellValue(beneficiario.getPersona().getTipoProductor() != null ? beneficiario.getPersona().getTipoProductor().name() : "");
            row.createCell(28).setCellValue(Optional.ofNullable(beneficiario.getPersona().getResponsable().getNombre()).orElse(""));
            row.createCell(29).setCellValue(Optional.ofNullable(beneficiario.getPersona().getOrganizacion()).map(Object::toString).orElse(""));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
        return outputStream.toByteArray();
    }

        public byte[] exportaPersonaExel(Long idResponsable, boolean activo) throws IOException {

        System.out.println("Exportando beneficiarios a excel");
        Responsable proyecto = responsableService.obtenerResponsablePorId(idResponsable);

        List<Persona> beneficiarios = personaRepository.findByResponsableIdAAndActivo(idResponsable, activo);

//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Beneficiarios Proyecto " + proyecto.getNombre());
        SXSSFWorkbook workbook = new SXSSFWorkbook(); // Utiliza SXSSFWorkbook en lugar de XSSFWorkbook
        workbook.setCompressTempFiles(true); // Opcional: comprimir archivos temporales
        Sheet sheet = workbook.createSheet("Beneficiarios Proyecto " + proyecto.getNombre());

        crearEncabezado(sheet);


        int rowNum = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Persona persona : beneficiarios) {
                Row row = sheet.createRow(rowNum++);

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

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
        return outputStream.toByteArray();
    }


    public String  obtenerNombreProyecto(Long idProyecto){
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);
        return proyecto.getNombre();
    }
    private void crearEncabezado(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] columnas = {"Dpi", "Primer nombre", "Segundo nombre", "Tercer Nombre", "Primer apellido", "Segundo apellido"
                ,"apellido de casada", "Nit", "Teléfono", "Fecha de nacimiento", "Etnia", "Genero", "Estado Civil", "Numero De Hijos",
                "Tipo Vivienda", "Código departamento", "Departamento", "Código municipio", "Municipio",
                "Comunidad", "Discapacidad auditiva", "Discapacidad motora", "Discapacidad intelectual",
                "Comunidad Lingüística", "Area", "Cultivo", "Vende Excedente cosecha", "Tipo productor",
                "Responsable", "Organización"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }
    }

    public String obtenerNombreResponsable(Long idResponsable) {
        Responsable responsable = responsableService.obtenerResponsablePorId(idResponsable);
        return responsable.getNombre();
    }

}
