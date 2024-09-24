package com.proyectograduacion.PGwebONG.service;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportarAExcelService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final ProyectoService proyectoService;

    public ExportarAExcelService(BeneficiarioRepository beneficiarioRepository, ProyectoService proyectoService){
        this.beneficiarioRepository = beneficiarioRepository;
        this.proyectoService = proyectoService;
    }

    public byte[] exportarBeneficiarioAExcel(Long idProyecto, boolean activo) throws IOException {

        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);

        List<Beneficiario> beneficiarios = beneficiarioRepository.findByProyectoIdAndActivo(idProyecto, activo);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Beneficiarios Proyecto " + proyecto.getNombre());

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Dpi");
        header.createCell(1).setCellValue("Primer nombre");
        header.createCell(2).setCellValue("Segundo nombre");
        header.createCell(3).setCellValue("Primer apellido");
        header.createCell(4).setCellValue("Segundo apellido");
        header.createCell(5).setCellValue("Nit");
        header.createCell(6).setCellValue("Teléfono");
        header.createCell(7).setCellValue("Fecha de nacimiento");
        header.createCell(8).setCellValue("Etnia");
        header.createCell(9).setCellValue("Genero");
        header.createCell(10).setCellValue("Estado Civil");
        header.createCell(11).setCellValue("Numero De Hijos");
        header.createCell(12).setCellValue("Tipo Vivienda");
        header.createCell(13).setCellValue("Código departamento");
        header.createCell(14).setCellValue("Departamento");
        header.createCell(15).setCellValue("Código municipio");
        header.createCell(16).setCellValue("Municipio");
        header.createCell(17).setCellValue("Comunidad");
        header.createCell(18).setCellValue("Discapacidad auditiva");
        header.createCell(19).setCellValue("Discapacidad motora");
        header.createCell(20).setCellValue("Discapacidad intellectual");
        header.createCell(21).setCellValue("Comunidad Lingüística");
        header.createCell(22).setCellValue("Area");
        header.createCell(23).setCellValue("Cultivo");
        header.createCell(24).setCellValue("Vende Excedente cosecha");
        header.createCell(25).setCellValue("Tipo productor");
        header.createCell(26).setCellValue("Responsable");
        header.createCell(27).setCellValue("Organización");

        int rowNum = 1;
        for ( Beneficiario beneficiario: beneficiarios){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Long.parseLong( beneficiario.getPersona().getDpi()));
            row.createCell(1).setCellValue(beneficiario.getPersona().getPrimerNombre());
            row.createCell(2).setCellValue(beneficiario.getPersona().getSegundoNombre());
            row.createCell(3).setCellValue(beneficiario.getPersona().getPrimerApellido());
            row.createCell(4).setCellValue(beneficiario.getPersona().getSegundoApellido());

            row.createCell(5).setCellValue(Long.parseLong(beneficiario.getPersona().getNIT()));
            row.createCell(6).setCellValue(Long.parseLong(beneficiario.getPersona().getTelefono()));

            row.createCell(7).setCellValue(beneficiario.getPersona().getFechaNacimiento().toString());
            row.createCell(8).setCellValue(beneficiario.getPersona().getEtnia());
            row.createCell(9).setCellValue(beneficiario.getPersona().getGenero().name());
            row.createCell(10).setCellValue(beneficiario.getPersona().getEstadoCivil());
            row.createCell(11).setCellValue(beneficiario.getPersona().getNumeroHijos());
            row.createCell(12).setCellValue(beneficiario.getPersona().getTipoVivienda());

            row.createCell(13).setCellValue(Long.parseLong( beneficiario.getPersona().getDireccion().getCodigoDepartamento()));
            row.createCell(14).setCellValue( beneficiario.getPersona().getDireccion().getNombreDepartamento());
            row.createCell(15).setCellValue(Long.parseLong( beneficiario.getPersona().getDireccion().getCodigoMunicipio()));
            row.createCell(16).setCellValue(beneficiario.getPersona().getDireccion().getNombreMunicipio());

            row.createCell(17).setCellValue(beneficiario.getPersona().getDireccion().getComunidad());
            row.createCell(18).setCellValue(beneficiario.getPersona().getDiscapacidad().isDiscapacidadAuditiva() ? "Si" : "No");
            row.createCell(19).setCellValue(beneficiario.getPersona().getDiscapacidad().isDiscapacidadMotora() ? "Si" : "No");
            row.createCell(20).setCellValue(beneficiario.getPersona().getDiscapacidad().isDicapacidadIntelectual() ? "Si" : "No");
            row.createCell(21).setCellValue(beneficiario.getPersona().getComunidadLinguistica());
            row.createCell(22).setCellValue(beneficiario.getPersona().getArea());
            row.createCell(23).setCellValue(beneficiario.getPersona().getCultivo());
            row.createCell(24).setCellValue(beneficiario.getPersona().isVendeExecedenteCosecha() ? "Si" : "No");
            row.createCell(25).setCellValue(beneficiario.getPersona().getTipoProductor().name());
            row.createCell(26).setCellValue(beneficiario.getPersona().getResponsable().getNombre());
            row.createCell(27).setCellValue(beneficiario.getPersona().getOrganizacion().toString());


        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public String  obtenerNombreProyecto(Long idProyecto){
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);
        return proyecto.getNombre();
    }

}
