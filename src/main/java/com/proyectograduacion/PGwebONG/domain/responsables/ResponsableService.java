package com.proyectograduacion.PGwebONG.domain.responsables;

import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ResponsableService {

    private final ResponsableRepository responsableRepository;

    public ResponsableService(ResponsableRepository responsableRepository) {
        this.responsableRepository = responsableRepository;
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


    public Responsable registrarResponsable(DatosRegistroResponsable datosRegistroDonador) {
        validarCorreoTelefono(datosRegistroDonador);
        Responsable newResponsable = new Responsable(datosRegistroDonador);
        responsableRepository.save(newResponsable);
        return newResponsable;

    }

    public Responsable modificarResponsable(DatosActualizarResponsable datosActualizarResponsable) {
        Responsable responsable = verificarExistenciaResponsable(datosActualizarResponsable.id());
        responsable.actualizarResponsable(datosActualizarResponsable);
        return responsable;
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
