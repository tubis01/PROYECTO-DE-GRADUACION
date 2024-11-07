package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.responsables.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/responsables")
@SecurityRequirement(name = "bearer-key")
public class ResponsableController {

    private final ResponsableService responsableService;

    public ResponsableController(ResponsableService responsableService) {
        this.responsableService = responsableService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleResponsable>>> listarResponsables(Pageable pageable,
                                                                                               PagedResourcesAssembler<DatosDetalleResponsable> assembler) {

        Page<DatosDetalleResponsable> responsables = responsableService.listarResponsablesActivos(pageable);

        // Convertir a PagedModel sin agregar enlaces adicionales
        PagedModel<EntityModel<DatosDetalleResponsable>> pagedModel = assembler.toModel(responsables, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleResponsable>>> listarResponsablesInactivos(Pageable pageable,
                                                                                                        PagedResourcesAssembler<DatosDetalleResponsable> assembler) {
        Page<DatosDetalleResponsable> responsables = responsableService.listarResponsablesInactivos(pageable);

        // Convertir a PagedModel sin agregar enlaces adicionales
        PagedModel<EntityModel<DatosDetalleResponsable>> pagedModel = assembler.toModel(responsables, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DatosDetalleResponsable>> obtenerResponsablePorId(@PathVariable Long id) {
        Responsable responsable = responsableService.obtenerResponsablePorId(id);
        DatosDetalleResponsable responsableDTO = new DatosDetalleResponsable(responsable);

        // Devolver la entidad sin enlaces adicionales
        EntityModel<DatosDetalleResponsable> entityModel = EntityModel.of(responsableDTO);
        return ResponseEntity.ok(entityModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<Responsable> registrarResponsable(@RequestBody String encryptedData,
                                                            UriComponentsBuilder uriBuilder) {
        System.out.println("datos recibidos: " + encryptedData);
        Responsable responsable = responsableService.registrarResponsable(encryptedData);
        // Construir la URI del nuevo recurso
        URI uri = uriBuilder.path("/responsables/{id}").buildAndExpand(responsable.getId()).toUri();
        return ResponseEntity.created(uri).body(responsable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modificar")
    @Transactional
//    public ResponseEntity<DatosDetalleResponsable> modificarResponsable(@RequestBody @Valid DatosActualizarResponsable datosActualizarResponsable) {
//        Responsable responsable = responsableService.modificarResponsable(datosActualizarResponsable);
//        DatosDetalleResponsable responsableDTO = new DatosDetalleResponsable(responsable);
//        return ResponseEntity.ok(responsableDTO);
//    }
    public ResponseEntity<DatosDetalleResponsable> modificarResponsable(@RequestBody String datosActualizarResponsable) {
        System.out.println("datos recibidos: " + datosActualizarResponsable);
        Responsable responsable = responsableService.modificarResponsable(datosActualizarResponsable);
        DatosDetalleResponsable responsableDTO = new DatosDetalleResponsable(responsable);
        return ResponseEntity.ok(responsableDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Responsable> eliminarResponsable(@PathVariable Long id) {
        responsableService.eliminarResponsable(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminarBD/{id}")
    @Transactional
    public ResponseEntity<Responsable> eliminarResponsableBD(@PathVariable Long id) {
        responsableService.eliminarResponsableBD(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activar/{id}")
    @Transactional
    public ResponseEntity<Responsable> activarResponsable(@PathVariable Long id) {
        responsableService.activarResponsable(id);
        return ResponseEntity.noContent().build();
    }
}

