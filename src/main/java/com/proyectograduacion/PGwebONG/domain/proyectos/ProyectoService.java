package com.proyectograduacion.PGwebONG.domain.proyectos;

import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
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
        if (!proyectoRepository.existsById(id)) {
            throw new validacionDeIntegridad("No existe un proyecto con el id proporcionado");
        }
        return proyectoRepository.getReferenceById(id);
    }

    public Proyecto registrarProyecto(DatosRegistroProyecto datosRegistroProyecto) {
        Proyecto newProyecto = new Proyecto(datosRegistroProyecto);
        proyectoRepository.save(newProyecto);
        return newProyecto;
    }

    public void eliminarProyecto(Long id) {
        Proyecto proyecto = verificarExistenciaProyecto(id);
        proyecto.eliminarProyecto();
        proyectoRepository.save(proyecto);
    }
}
