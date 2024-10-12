package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.responsables.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/responsables")
public class ResponsableController {

    private final ResponsableService responsableService;

    public  ResponsableController(ResponsableService responsableService){
        this.responsableService = responsableService;
    }

    /*
    * Método que lista los donadores
    *
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleResponsable>>> listarDonadores(Pageable pageable,
                                                                                            PagedResourcesAssembler<DatosDetalleResponsable> assembler) {

        Page<DatosDetalleResponsable> responsables = responsableService.listarResponsablesActivos(pageable);

        PagedModel<EntityModel<DatosDetalleResponsable>> pagedModel = assembler.toModel(responsables, donador ->{
            Link selfLink  = linkTo(methodOn(ResponsableController.class).obtenerResponsablePorId(donador.id())).withSelfRel();
            Link eliminarLink = linkTo(methodOn(ResponsableController.class).eliminarResponsable(donador.id())).withRel("eliminar");
            return EntityModel.of(donador, selfLink, eliminarLink);

        });
        return ResponseEntity.ok(pagedModel);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleResponsable>>> listarDonadoresInactivos(Pageable pageable,
                                                                                                 PagedResourcesAssembler<DatosDetalleResponsable> assembler) {
        Page<DatosDetalleResponsable> responsables = responsableService.listarResponsablesInactivos(pageable);

        PagedModel<EntityModel<DatosDetalleResponsable>> pagedModel = assembler.toModel(responsables, responsable -> {
            Link selfLink = linkTo(methodOn(ResponsableController.class).obtenerResponsablePorId(responsable.id())).withSelfRel();
            Link activarLink = linkTo(methodOn(ResponsableController.class).activarResponsable(responsable.id())).withRel("activar");
            Link eliminarBdLink = linkTo(methodOn(ResponsableController.class).eliminarResponsableBD(responsable.id())).withRel("eliminarBD");
            return EntityModel.of(responsable, selfLink, activarLink, eliminarBdLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DatosDetalleResponsable>> obtenerResponsablePorId(@PathVariable Long id) {
        Responsable responsable = responsableService.obtenerResponsablePorId(id);
        DatosDetalleResponsable donadorDTO = new DatosDetalleResponsable(responsable);

        // Enlaces específicos para un recurso individual
        EntityModel<DatosDetalleResponsable> entityModel = EntityModel.of(donadorDTO);
        entityModel.add(linkTo(methodOn(ResponsableController.class).obtenerResponsablePorId(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(ResponsableController.class).modificarResponsable(new DatosActualizarResponsable(responsable.getId(),null,null,null, null,null,null))).withRel("modificar"));
        entityModel.add(linkTo(methodOn(ResponsableController.class).eliminarResponsable(id)).withRel("eliminar"));

        return ResponseEntity.ok(entityModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleResponsable> registrarResponsable(@RequestBody @Valid DatosRegistroResponsable datosRegistroResponsable,
                                                                        UriComponentsBuilder uriBuilder) {

        Responsable responsable = responsableService.registrarResponsable(datosRegistroResponsable);

        DatosDetalleResponsable donadorDTO = new DatosDetalleResponsable(responsable);
        URI uri = uriBuilder.path("/donadores/{id}").buildAndExpand(responsable.getId()).toUri();
        return ResponseEntity.created(uri).body(donadorDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modificar")
    @Transactional
    public ResponseEntity<DatosDetalleResponsable> modificarResponsable(@RequestBody @Valid DatosActualizarResponsable datosActualizarResponsable) {
        Responsable responsable = responsableService.modificarResponsable(datosActualizarResponsable);
        DatosDetalleResponsable donadorDTO = new DatosDetalleResponsable(responsable);
        return ResponseEntity.ok(donadorDTO);
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
