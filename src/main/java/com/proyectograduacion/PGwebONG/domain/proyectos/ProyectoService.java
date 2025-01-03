package com.proyectograduacion.PGwebONG.domain.proyectos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.validaciones.ValidadorProyectos;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import com.proyectograduacion.PGwebONG.service.EncriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    private final List<ValidadorProyectos> validadores;
    private final ProyectoRepository proyectoRepository;
    private final BeneficiarioRepository beneficiarioRepository;
    private final ObjectMapper objectMapper;
    private final EncriptionService encriptionService;

    public ProyectoService(ProyectoRepository proyectoRepository, List<ValidadorProyectos> validadores, BeneficiarioRepository beneficiarioRepository,
                           ObjectMapper objectMapper, EncriptionService encriptionService) {
        this.proyectoRepository = proyectoRepository;
        this.validadores = validadores;
        this.beneficiarioRepository = beneficiarioRepository;
        this.objectMapper = objectMapper;
        this.encriptionService = encriptionService;
    }

    public Page<DatosDetalleProyecto> listarProyectos(Pageable pageable) {
        return proyectoRepository.findByActivoTrue(pageable)
                .map(DatosDetalleProyecto::new);
    }


    public Page<DatosDetalleProyecto> listarProyectosInactivos(Pageable pageable) {
        return  proyectoRepository.findByActivoFalse(pageable)
                .map(DatosDetalleProyecto::new);
    }

    public Proyecto obtenerProyectoPorId(Long id) {
        return verificarExistenciaProyecto(id);
    }

    private Proyecto verificarExistenciaProyecto(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new validacionDeIntegridad("No existe el proyecto con id: " + id));
    }

    public Proyecto registrarProyecto(String datosEncriptados) {
        String datosDesencriptado = encriptionService.decrypt(datosEncriptados);
        System.out.println(datosDesencriptado);
        DatosRegistroProyecto datosRegistroProyecto = convertToDatosRegistroProyecto(datosDesencriptado);
        validadores.forEach(validador -> validador.validar(datosRegistroProyecto));
        Proyecto newProyecto = new Proyecto(datosRegistroProyecto);
        proyectoRepository.save(newProyecto);
        return newProyecto;
    }

    public void finalizarProyecto(Long id) {
        Proyecto proyecto = verificarExistenciaProyecto(id);
        proyecto.finalizarProyecto();
        proyectoRepository.save(proyecto);

//        desactivarBeneficiariosDeProyeto(proyecto);
    }

    public void desactivarBeneficiariosDeProyeto(Proyecto proyecto){
        List<Beneficiario> beneficiarios = beneficiarioRepository.findByProyecto(proyecto);
        for (Beneficiario beneficiario : beneficiarios) {
            beneficiario.desactivar();
        }
    }

    public Proyecto modificarProyecto(DatosActualizarProyecto actualizarProyecto) {
        Proyecto proyecto = verificarExistenciaProyecto(actualizarProyecto.id());
        proyecto.actualizarProyecto(actualizarProyecto);
        proyectoRepository.save(proyecto);
        return proyecto;
    }

    public List<DatosDetalleProyecto> buscarPorDpiParcial(String dpi, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return proyectoRepository.findByProyectoContaining(dpi, pageRequest)
                .map(DatosDetalleProyecto::new).getContent();
    }

    private DatosRegistroProyecto convertToDatosRegistroProyecto(String datosDesencriptado) {
        try {
            return objectMapper.readValue(datosDesencriptado, DatosRegistroProyecto.class);
        } catch (Exception e) {
            throw new validacionDeIntegridad("Error al convertir los datos desencriptados.");
        }
    }
}
