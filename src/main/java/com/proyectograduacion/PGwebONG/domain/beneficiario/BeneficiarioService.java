package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.proyectograduacion.PGwebONG.domain.beneficiario.validaciones.IValidadorBeneficiario;
import com.proyectograduacion.PGwebONG.domain.personas.Persona;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaRepository;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaService;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoRepository;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoService;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
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

    private final List<IValidadorBeneficiario> validadores;

    public BeneficiarioService(BeneficiarioRepository beneficiarioRepository, PersonaService personaService,
                               ProyectoService proyectoService, PersonaRepository personaRepository, ProyectoRepository proyectoRepository,
                               List<IValidadorBeneficiario> validadores) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.personaService = personaService;
        this.proyectoService = proyectoService;
        this.personaRepository = personaRepository;
        this.proyectoRepository = proyectoRepository;
        this.validadores = validadores;
    }


    public Page<DatosDetalleBeneficiario> listarBeneficiarios(Pageable pageable) {
        return beneficiarioRepository.findByActivoTrue(pageable)
                .map(DatosDetalleBeneficiario::new);
    }



    @Transactional
    public Beneficiario registrarBeneficiario(DatosregistroBeneficiario datosregistroBeneficiario){


        verificarProyectoYPersona(datosregistroBeneficiario.proyecto(), datosregistroBeneficiario.dpi());

        validadores.forEach(validador -> validador.validar(datosregistroBeneficiario));

        Persona persona = personaService.obtenerPersonaPorDPI(datosregistroBeneficiario.dpi());
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(datosregistroBeneficiario.proyecto());


        Beneficiario beneficiario = new Beneficiario(persona, proyecto);
        beneficiarioRepository.save(beneficiario);
        return beneficiario;
    }


    public Beneficiario obtenerBeneficiarioPorId(Long id) {
        return verificarExistenciaBeneficiario(id);
    }

    public void verificarProyectoYPersona(Long idProyecto, String dpi){
        if(proyectoRepository.findById(idProyecto).isEmpty()){
            throw new validacionDeIntegridad("El proyecto no existe");
        }
        if(!personaRepository.existsByDpi(dpi)){
            throw new validacionDeIntegridad("La persona no existe");
        }
    }


    public void eliminarBeneficiario(Long id) {

        Beneficiario beneficiario = verificarExistenciaBeneficiario(id);
        beneficiario.desactivar();
    }

    public Beneficiario verificarExistenciaBeneficiario(Long id) {
        if(!beneficiarioRepository.existsById(id)){
            throw new validacionDeIntegridad("No existe un beneficiario con el id proporcionado");
        }
        return beneficiarioRepository.getReferenceById(id);
    }
}
