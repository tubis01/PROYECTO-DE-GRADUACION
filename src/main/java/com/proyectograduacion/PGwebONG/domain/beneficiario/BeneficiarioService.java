package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectograduacion.PGwebONG.domain.beneficiario.validaciones.IValidadorBeneficiario;
import com.proyectograduacion.PGwebONG.domain.personas.Persona;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaRepository;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaService;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoService;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import com.proyectograduacion.PGwebONG.service.EncriptionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiarioService {
    private final BeneficiarioRepository beneficiarioRepository;
    private final PersonaService personaService;
    private final ProyectoService proyectoService;
    private final PersonaRepository personaRepository;
    private final ProyectoRepository proyectoRepository;
    private final EncriptionService encriptionService;
    private final ObjectMapper objectMapper;

    private final List<IValidadorBeneficiario> validadores;

    public BeneficiarioService(BeneficiarioRepository beneficiarioRepository, PersonaService personaService,
                               ProyectoService proyectoService, PersonaRepository personaRepository, ProyectoRepository proyectoRepository,
                               List<IValidadorBeneficiario> validadores, EncriptionService encriptionService, ObjectMapper objectMapper) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.personaService = personaService;
        this.proyectoService = proyectoService;
        this.personaRepository = personaRepository;
        this.proyectoRepository = proyectoRepository;
        this.validadores = validadores;
        this.encriptionService = encriptionService;
        this.objectMapper = objectMapper;
    }

    public Page<DatosDetalleBeneficiario> listarBeneficiarios(Pageable pageable) {

        return beneficiarioRepository.findByActivoTrue(pageable)
                .map(DatosDetalleBeneficiario::new);
    }


    public List<DatosDetalleBeneficiario> buscarPorDpiParcial(String dpi, int page, int limit){
        PageRequest pageRequest = PageRequest.of(page, limit);
        return beneficiarioRepository.findByPersonaDpiContaining(dpi, pageRequest)
                .map(DatosDetalleBeneficiario::new).getContent();
    }

        @Transactional
        public Beneficiario registrarBeneficiario(String datpsEncriptados){

        String datosDesencriptados = encriptionService.decrypt(datpsEncriptados);
            System.out.println(datosDesencriptados);

            DatosregistroBeneficiario datosregistroBeneficiario = convertToDatosRegistroBeneficiario(datosDesencriptados);

            verificarProyectoYPersona(datosregistroBeneficiario.proyecto(), datosregistroBeneficiario.dpi());
            validadores.forEach(validador -> validador.validar(datosregistroBeneficiario));

            Persona persona = personaService.obtenerPersonaPorDPI(datosregistroBeneficiario.dpi());
            Proyecto proyecto = proyectoService.obtenerProyectoPorId(datosregistroBeneficiario.proyecto());

            Beneficiario beneficiario = new Beneficiario(persona, proyecto);
            beneficiarioRepository.save(beneficiario);
            return beneficiario;
        }

    public DatosDetalleBeneficiario modificarBeneficiario(DatosModificarBeneficiario datosActualizarBeneficiario) {
        verificarProyectoYPersona(datosActualizarBeneficiario.proyecto(), datosActualizarBeneficiario.dpi());

        Persona persona = personaService.obtenerPersonaPorDPI(datosActualizarBeneficiario.dpi());
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(datosActualizarBeneficiario.proyecto());

        // Validar que el nuevo proyecto esté activo
        if (!proyecto.isActivo()) {
            throw new validacionDeIntegridad("No se puede asignar un proyecto inactivo");
        }

        Beneficiario beneficiario = verificarExistenciaBeneficiario(datosActualizarBeneficiario.id());

        // Solo actualiza el proyecto, los demás campos no son modificables
        beneficiario.actualizarBeneficiario(proyecto);
        beneficiarioRepository.save(beneficiario);

        return new DatosDetalleBeneficiario(beneficiario);
    }
    public Beneficiario obtenerBeneficiarioPorId(Long id) {
        return verificarExistenciaBeneficiario(id);
    }

    public void verificarProyectoYPersona(Long idProyecto, String dpi){
        if(proyectoRepository.findById(idProyecto).isEmpty()){
            throw new validacionDeIntegridad("El proyecto no existe");
        }
        if(!personaRepository.existsByDpi(dpi)){
            throw new validacionDeIntegridad("No existe la persona Con el {{ DPI }} proporcionado");
        }
    }



    public void desactivarBeneficiario(Long id) {
        Beneficiario beneficiario = verificarExistenciaBeneficiario(id);
        beneficiario.desactivar();
    }


    public Beneficiario verificarExistenciaBeneficiario(Long id) {
        if(!beneficiarioRepository.existsById(id)){
            throw new validacionDeIntegridad("No existe un beneficiario con el id proporcionado");
        }
        return beneficiarioRepository.getReferenceById(id);
    }

    public List<DatosDetalleBeneficiario> buscarPorNombreProyecto(String nombreProyecto, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return beneficiarioRepository.findByProyectoNombreContaining(nombreProyecto, pageRequest)
                .map(DatosDetalleBeneficiario::new).getContent();
    }

    private DatosregistroBeneficiario convertToDatosRegistroBeneficiario(String datosDesencriptado) {
        try {
            return objectMapper.readValue(datosDesencriptado, DatosregistroBeneficiario.class);
        } catch (Exception e) {
            throw new validacionDeIntegridad("Error al convertir los datos desencriptados.");
        }
    }


}

