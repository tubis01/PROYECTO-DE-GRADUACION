package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.proyectograduacion.PGwebONG.domain.personas.Persona;
import com.proyectograduacion.PGwebONG.domain.personas.PersonaService;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import com.proyectograduacion.PGwebONG.domain.proyectos.ProyectoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BeneficiarioService {
    private final BeneficiarioRepository beneficiarioRepository;
    private final PersonaService personaService;
    private final ProyectoService proyectoService;

    public BeneficiarioService(BeneficiarioRepository beneficiarioRepository, PersonaService personaService,
                               ProyectoService proyectoService) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.personaService = personaService;
        this.proyectoService = proyectoService;
    }


    public Page<DatosDetalleBeneficiario> listarBeneficiarios(Pageable pageable) {
        return beneficiarioRepository.findAll(pageable).map(DatosDetalleBeneficiario::new);
    }

    @Transactional
    public Beneficiario registrarBeneficiario(DatosregistroBeneficiario datosregistroBeneficiario){
        Persona persona = personaService.obtenerPersonaPorDPI(datosregistroBeneficiario.dpi());

        if(beneficiarioRepository.existsByPersona(persona)){
            throw new IllegalStateException("El beneficiario ya existe");
        }

        Proyecto proyecto = proyectoService.obtenerProyectoPorId(datosregistroBeneficiario.proyecto());
        Beneficiario beneficiario = new Beneficiario(persona, proyecto);
        beneficiarioRepository.save(beneficiario);
        return beneficiario;
    }


    public Beneficiario obtenerBeneficiarioPorId(Long id) {
        return beneficiarioRepository.findById(id).orElseThrow(() -> new IllegalStateException("El beneficiario no existe"));
    }


}
