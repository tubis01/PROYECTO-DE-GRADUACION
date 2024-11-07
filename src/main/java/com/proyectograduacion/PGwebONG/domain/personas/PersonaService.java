package com.proyectograduacion.PGwebONG.domain.personas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosDetalleBeneficiario;
import com.proyectograduacion.PGwebONG.domain.organizacion.Organizacion;
import com.proyectograduacion.PGwebONG.domain.organizacion.OrganizacionRepository;
import com.proyectograduacion.PGwebONG.domain.responsables.Responsable;
import com.proyectograduacion.PGwebONG.domain.responsables.ResponsableRepository;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import com.proyectograduacion.PGwebONG.service.EncriptionService;
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
    private final EncriptionService encriptionService;
    private final ObjectMapper objectMapper;

    public PersonaService(PersonaRepository personaRepository, ResponsableRepository responsableRepository,
                          OrganizacionRepository organizacionRepository, EncriptionService encriptionService,
                          ObjectMapper objectMapper) {
        this.personaRepository = personaRepository;
        this.responsableRepository = responsableRepository;
        this.organizacionRepository = organizacionRepository;
        this.encriptionService = encriptionService;
        this.objectMapper = objectMapper;
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


    public Persona registrarPersona(String datosEncriptados) {

        String datosDesencriptado = encriptionService.decrypt(datosEncriptados);
        DatosRegistroPersona datosRegistroPersona = converToDatosRegistroPersona(datosDesencriptado);
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


    public DatosDetallePersona modificarPersona(String  datosEncriptados) {
        String datosDesencriptado = encriptionService.decrypt(datosEncriptados);
        System.out.println(datosDesencriptado);
        DatosActualizarPersona datosActualizarPersona = converToDatosActualizarPersona(datosDesencriptado);
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

    private DatosRegistroPersona converToDatosRegistroPersona(String decryptedData) {
        try {
            return objectMapper.readValue(decryptedData, DatosRegistroPersona.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir los datos desencriptados.", e);
        }
    }

    private DatosActualizarPersona converToDatosActualizarPersona(String decryptedData) {
        try {
            return objectMapper.readValue(decryptedData, DatosActualizarPersona.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir los datos desencriptados.", e);
        }
    }
}