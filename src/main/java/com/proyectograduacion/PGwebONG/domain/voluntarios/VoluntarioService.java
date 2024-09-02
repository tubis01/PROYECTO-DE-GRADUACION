package com.proyectograduacion.PGwebONG.domain.voluntarios;

import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;

    public VoluntarioService(VoluntarioRepository voluntarioRepository) {
        this.voluntarioRepository = voluntarioRepository;
    }

    public Page<DatosDetalleVoluntario> listarVoluntarios(Pageable pageable) {
        return voluntarioRepository.findByActivoTrue(pageable)
                .map(DatosDetalleVoluntario::new);
    }

    public Voluntario obtenerVoluntarioPorId(Long id) {

        return verficarExistenciaVoluntario(id);

    }
    public Page<DatosDetalleVoluntario> listarVoluntariosInactivos(Pageable pageable) {
        return voluntarioRepository.findByActivoFalse(pageable)
                .map(DatosDetalleVoluntario::new);
    }

    private Voluntario verficarExistenciaVoluntario(Long id) {
        if(!voluntarioRepository.existsById(id)){
            throw new validacionDeIntegridad("No existe un voluntario con el id proporcionado");
        }
        return voluntarioRepository.getReferenceById(id);
    }

    public Voluntario registrarVoluntario(DatosRegistroVoluntario datosRegistroVoluntario) {
        if(voluntarioRepository.existsByCorreo(datosRegistroVoluntario.correo())){
            throw new validacionDeIntegridad("El correo ya fue registrado");
        }
        if(voluntarioRepository.existsByTelefono(datosRegistroVoluntario.telefono())){
            throw new validacionDeIntegridad("El telefono ya fue registrado");
        }

        Voluntario newVoluntario = new Voluntario(datosRegistroVoluntario);
        voluntarioRepository.save(newVoluntario);
        return newVoluntario;
    }

    public Voluntario modificarVoluntario(DatosActualizarVoluntario datosActualizarVoluntario) {
        Voluntario voluntario = verficarExistenciaVoluntario(datosActualizarVoluntario.id());
        voluntario.actualizarVoluntario(datosActualizarVoluntario);
        return voluntario;
    }

    public void eliminarVoluntario(Long id) {
        Voluntario voluntario = verficarExistenciaVoluntario(id);
        voluntario.desactivar();
    }

    public void eliminarVoluntarioBD(Long id) {
        Voluntario voluntario = verficarExistenciaVoluntario(id);
        voluntarioRepository.delete(voluntario);
    }

    public void activarVoluntario(Long id) {
        Voluntario voluntario = verficarExistenciaVoluntario(id);
        voluntario.activar();
    }

}
