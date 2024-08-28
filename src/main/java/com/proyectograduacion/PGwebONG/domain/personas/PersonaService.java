package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }


    public Page<DatosDetallePersona> listarPersonas(Pageable pageable) {
        return personaRepository.findByActivoTrue(pageable)
                .map(DatosDetallePersona::new);
    }

    public Persona registrarPersona(DatosRegistroPersona datosRegistroPersona) {
        if (personaRepository.existsByDpi(datosRegistroPersona.dpi())) {
            throw new validacionDeIntegridad("El DPI ya fue registrado");
        }
        if(personaRepository.existsByNIT(datosRegistroPersona.NIT())){
            throw new validacionDeIntegridad("El NIT ya fue registrado");
        }
        if(personaRepository.existsByTelefono(datosRegistroPersona.telefono())){
            throw new validacionDeIntegridad("El telefono ya fue registrado");
        }
            Persona newPerson = new Persona(datosRegistroPersona);
            personaRepository.save(newPerson);
            return newPerson;
    }

    public Persona obtenerPersonaPorDPI(String dpi) {
        return verificarExistencia(dpi);
    }


    public DatosDetallePersona modificarPersona(DatosActualizarPersona datosActualizarPersona) {
        Persona persona = verificarExistencia(datosActualizarPersona.dpi());
        persona.actualizarPersona(datosActualizarPersona);
        return new DatosDetallePersona(persona);
    }

    public void eliminarPersona(String dpi) {
        Persona persona = verificarExistencia(dpi);
        persona.eliminarPersona();
    }

    private Persona verificarExistencia(String dpi) {
        if (!personaRepository.existsByDpi(dpi)) {
            throw new validacionDeIntegridad("No existe una persona con el DPI proporcionado");
        }
        return personaRepository.getReferenceByDpi(dpi);
    }

}