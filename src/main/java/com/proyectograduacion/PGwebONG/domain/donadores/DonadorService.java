package com.proyectograduacion.PGwebONG.domain.donadores;

import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DonadorService {

    private final DonadorRepository donadorRepository;

    public DonadorService(DonadorRepository donadorRepository) {
        this.donadorRepository = donadorRepository;
    }

//    listar donadores
    public Page<DatosDetalleDonador> listarDonadores(Pageable pageable) {
        return donadorRepository.findByActivoTrue(pageable)
                .map(DatosDetalleDonador::new);
    }
    public Page<DatosDetalleDonador> listarDonadoresInactivos(Pageable pageable) {
        return  donadorRepository.findByActivoFalse(pageable)
                .map(DatosDetalleDonador::new);
    }
//    obtener donador por id
    public Donador obtenerDonadorPorId(Long id) {
        return verificarExistenciaDonador(id);

    }

//    registrar donador
    public Donador registrarDonador(DatosRegistroDonador datosRegistroDonador) {
        if(donadorRepository.existsByCorreo(datosRegistroDonador.correo())){
            throw new validacionDeIntegridad("El correo ya fue registrado");
        }
        if(donadorRepository.existsByTelefono(datosRegistroDonador.telefono())){
            throw new validacionDeIntegridad ("El telefono ya fue registrado");
        }
        Donador newDonador = new Donador(datosRegistroDonador);
        donadorRepository.save(newDonador);
        return newDonador;

    }

//    modificar donador

    public Donador modificarDonador(DatosActualizarDonador datosActualizarDonador) {
        Donador donador = verificarExistenciaDonador(datosActualizarDonador.id());
        donador.actualizarDonador(datosActualizarDonador);
        return donador;
    }

//    validar existencia de donador
    private Donador verificarExistenciaDonador(Long id) {
        if (!donadorRepository.existsById(id)) {
            throw new validacionDeIntegridad("No existe un donador con el id proporcionado");
        }
        return donadorRepository.getReferenceById(id);
    }



    public void eliminarDonador(Long id) {
        Donador donador = verificarExistenciaDonador(id);
        donador.desactivar();
    }


    public void eliminarDonadorBD(Long id) {
        Donador donador = verificarExistenciaDonador(id);
        donadorRepository.delete(donador);
    }

    public void activarDonador(Long id) {
        Donador donador = verificarExistenciaDonador(id);
        donador.activar();
    }


}
