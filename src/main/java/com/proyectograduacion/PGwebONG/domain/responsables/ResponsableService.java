package com.proyectograduacion.PGwebONG.domain.responsables;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import com.proyectograduacion.PGwebONG.service.EncriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ResponsableService {

    private final ResponsableRepository responsableRepository;
    private final EncriptionService encriptionService;
    private final ObjectMapper objectMapper;

    public ResponsableService(ResponsableRepository responsableRepository,
                              EncriptionService encriptionService, ObjectMapper objectMapper) {
        this.responsableRepository = responsableRepository;
        this.encriptionService = encriptionService;
        this.objectMapper = objectMapper;
    }

    
    public Page<DatosDetalleResponsable> listarResponsablesActivos(Pageable pageable) {
        return responsableRepository.findByActivoTrue(pageable)
                .map(DatosDetalleResponsable::new);
    }

    public Page<DatosDetalleResponsable> listarResponsablesInactivos(Pageable pageable) {
        return responsableRepository.findByActivoFalse(pageable)
                .map(DatosDetalleResponsable::new);
    }

    public Responsable obtenerResponsablePorId(Long id) {
        return verificarExistenciaResponsable(id);
    }

    public Responsable registrarResponsable(String encryptedData) {
        // Desencripta el payload completo
        String decryptedData = encriptionService.decrypt(encryptedData);
        System.out.println(decryptedData);
        // Convierte el JSON desencriptado a un objeto DatosRegistroResponsable
        DatosRegistroResponsable datosRegistroResponsable = convertToDatosRegistroResponsable(decryptedData);

        // Valida que el correo y el teléfono no estén duplicados\
        validarCorreoTelefono(datosRegistroResponsable);

        // Crea y guarda el objeto Responsable
        Responsable newResponsable = new Responsable(datosRegistroResponsable);
        responsableRepository.save(newResponsable);
        return newResponsable;
    }

    private DatosRegistroResponsable convertToDatosRegistroResponsable(String decryptedData) {
        try {
            // Convierte el string JSON a un objeto DatosRegistroResponsable
            return objectMapper.readValue(decryptedData, DatosRegistroResponsable.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir los datos desencriptados.", e);
        }
        }

 public Responsable modificarResponsable(String datosEncriptados) {

        String decryptedData = encriptionService.decrypt(datosEncriptados);
        DatosActualizarResponsable datosActualizarResponsable = convertToDatosActualizarResponsable(decryptedData);
        Responsable responsable = verificarExistenciaResponsable(datosActualizarResponsable.id());
        responsable.actualizarResponsable(datosActualizarResponsable);
        return responsable;
    }

    private DatosActualizarResponsable convertToDatosActualizarResponsable(String decryptedData) {
        try {
            // Convierte el string JSON a un objeto DatosActualizarResponsable
            return objectMapper.readValue(decryptedData, DatosActualizarResponsable.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir los datos desencriptados.", e);
        }
    }

    public void eliminarResponsable(Long id) {
        Responsable responsable = verificarExistenciaResponsable(id);
        responsable.desactivar();
    }

    public void eliminarResponsableBD(Long id) {
        Responsable responsable = verificarExistenciaResponsable(id);
        responsableRepository.delete(responsable);
    }

    public void activarResponsable(Long id) {
        Responsable responsable = verificarExistenciaResponsable(id);
        responsable.activar();
    }

    private Responsable verificarExistenciaResponsable(Long id) {
        if (!responsableRepository.existsById(id)) {
            throw new validacionDeIntegridad("No existe un responsable con el id proporcionado");
        }
        return responsableRepository.getReferenceById(id);
    }


    private void validarCorreoTelefono(DatosRegistroResponsable datosRegistroDonador) {
        if(responsableRepository.existsByCorreo(datosRegistroDonador.correo())){
            throw new validacionDeIntegridad("Ya existe el {{ correo }} proporcionado");
        }
        if(responsableRepository.existsByTelefono(datosRegistroDonador.telefono())){
            throw new validacionDeIntegridad ("Ya existe el {{ teléfono }} proporcionado");
        }
    }
}
