package com.proyectograduacion.PGwebONG.domain.personas;

import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosDetalleBeneficiario;
import com.proyectograduacion.PGwebONG.domain.organizacion.Organizacion;
import com.proyectograduacion.PGwebONG.domain.organizacion.OrganizacionRepository;
import com.proyectograduacion.PGwebONG.domain.responsables.Responsable;
import com.proyectograduacion.PGwebONG.domain.responsables.ResponsableRepository;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {
    //    inyeccin de dependencias
    private final PersonaRepository personaRepository;
    private final ResponsableRepository responsableRepository;
    private final OrganizacionRepository organizacionRepository;

    public PersonaService(PersonaRepository personaRepository,
                          ResponsableRepository responsableRepository,
                          OrganizacionRepository organizacionRepository) {
        this.personaRepository = personaRepository;
        this.responsableRepository = responsableRepository;
        this.organizacionRepository = organizacionRepository;
    }

    // listar personas activas
    public Page<DatosDetallePersona> listarPersonas(Pageable pageable) {
        return personaRepository.findByActivoTrue(pageable)
                .map(DatosDetallePersona::new);
    }

    //    listar personas inactivas
    public Page<DatosDetallePersona> listarPersonasInactivas(Pageable pageable) {
        return personaRepository.findByActivoFalse(pageable)
                .map(DatosDetallePersona::new);
    }

    //    registrar persona
    public Persona registrarPersona(DatosRegistroPersona datosRegistroPersona) {
        validarUnicidadPersona(datosRegistroPersona);
        Responsable responsable = validarResponsable(datosRegistroPersona.responsable());
        Organizacion organizacion = validarOrganizacion(datosRegistroPersona.organizacion());

        Persona newPerson = new Persona(datosRegistroPersona, responsable, organizacion);
        responsable.agregarPersona(newPerson);
        personaRepository.save(newPerson);
        return newPerson;
    }



    public Persona obtenerPersonaPorDPI(String dpi) {
        return verificarExistencia(dpi);
    }


    public DatosDetallePersona modificarPersona(DatosActualizarPersona datosActualizarPersona) {
        Persona persona = verificarExistencia(datosActualizarPersona.DPI());
        Responsable responsable = datosActualizarPersona.responsable() != null ?
                validarResponsable(Long.valueOf(datosActualizarPersona.responsable())) : persona.getResponsable();
        Organizacion organizacion = datosActualizarPersona.organizacion() != null ?
                validarOrganizacion(Long.valueOf(datosActualizarPersona.organizacion())) : persona.getOrganizacion();
        persona.actualizarPersona(datosActualizarPersona, responsable, organizacion);
        return new DatosDetallePersona(persona);
    }

    //    eliminacion logica
    public void eliminarPersona(String dpi) {
        Persona persona = verificarExistencia(dpi);
        persona.eliminarPersona();
    }

    //    eliminacion fisica
    public void eliminarPersonaBD(String dpi) {
        Persona persona = verificarExistencia(dpi);
        personaRepository.delete(persona);
    }

    public void activarPersona(String dpi) {
        Persona persona = verificarExistencia(dpi);
        persona.activarPersona();
    }

    public Persona verificarExistencia(String dpi) {
        if (!personaRepository.existsByDpi(dpi)) {
            throw new validacionDeIntegridad("No existe una persona con el {{ DPI }} proporcionado");
        }
        return personaRepository.getReferenceByDpi(dpi);
    }

    private Responsable validarResponsable(Long responsableId) {
        return responsableRepository.findById(responsableId)
                .orElseThrow(() -> new validacionDeIntegridad("No existe un responsable con el id proporcionado"));
    }

    private Organizacion validarOrganizacion(Long organizacion) {
        return organizacionRepository.findById(organizacion)
                .orElseThrow(() -> new validacionDeIntegridad("No existe una organización con el id proporcionado"));

    }


    private void validarUnicidadPersona(DatosRegistroPersona datosRegistroPersona) {
        if (personaRepository.existsByDpi(datosRegistroPersona.DPI())) {
            throw new validacionDeIntegridad("Ya existe una persona con ese {{ DPI }}.");
        }
        if (datosRegistroPersona.NIT() != null && !datosRegistroPersona.NIT().isEmpty()) {
            // Verificar si el NIT ya existe solo si no está vacío o nulo
            if (personaRepository.existsByNIT(datosRegistroPersona.NIT())) {
                throw new validacionDeIntegridad("Ya existe una persona con ese {{ NIT }}.");
            }
        }

        if (personaRepository.existsByTelefono(datosRegistroPersona.telefono())) {
            throw new validacionDeIntegridad("Ya existe el {{ teléfono }} registrado");
        }
    }

    public List<DatosDetallePersona> buscarPorDpiParcial(String dpi, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return personaRepository.findByDpiContaining(dpi, pageRequest)
                .map(DatosDetallePersona::new).getContent();
    }
}