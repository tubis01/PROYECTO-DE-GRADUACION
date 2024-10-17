package com.proyectograduacion.PGwebONG.domain.organizacion;

import org.springframework.stereotype.Service;

@Service
public class OrganizacionService {

    private final OrganizacionRepository organizacionRepository;

    public OrganizacionService(OrganizacionRepository organizacionRepository) {
        this.organizacionRepository = organizacionRepository;
    }

    public Organizacion obtenerOrganizacionPorId(Long id) {
        if (!organizacionRepository.existsById(id)) {
            throw new IllegalStateException("La organizacion con id " + id + " no existe");
        } else {
            return organizacionRepository.getReferenceById(id);
        }

    }
}
