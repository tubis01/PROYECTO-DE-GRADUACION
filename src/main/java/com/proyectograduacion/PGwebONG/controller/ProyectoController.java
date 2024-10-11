package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.proyectos.*;
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
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    /**
     * Lista los proyectos.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de proyectos.
     */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleProyecto>>> listarProyectos(Pageable pageable,
                                                                   PagedResourcesAssembler<DatosDetalleProyecto> assembler) {

        Page<DatosDetalleProyecto> proyectos = proyectoService.listarProyectos(pageable);
        PagedModel<EntityModel<DatosDetalleProyecto>> pagedModel = assembler.toModel(proyectos, proyecto ->{
            Link selfLink  = linkTo(methodOn(ProyectoController.class).obtenerProyectoPorId(proyecto.id())).withSelfRel();
            Link eliminarLink = linkTo(methodOn(ProyectoController.class).finalizarProyecto(proyecto.id())).withRel("eliminar");
            return EntityModel.of(proyecto, selfLink, eliminarLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<DatosDetalleProyecto>> buscarPorDpiParcial(
            @RequestParam String term,
            @RequestParam int page,
            @RequestParam int size){
        List<DatosDetalleProyecto> beneficiarios = proyectoService.buscarPorDpiParcial(term, page, size);
        return ResponseEntity.ok(beneficiarios);
    }


    /**
     * Lista los proyectos inactivos.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de proyectos inactivos.
     */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleProyecto>>> listarProyectosInactivos(Pageable pageable,
                                                                   PagedResourcesAssembler<DatosDetalleProyecto> assembler) {

        Page<DatosDetalleProyecto> proyectos = proyectoService.listarProyectosInactivos(pageable);
        PagedModel<EntityModel<DatosDetalleProyecto>> pagedModel = assembler.toModel(proyectos, proyecto ->{
            Link selfLink  = linkTo(methodOn(ProyectoController.class).obtenerProyectoPorId(proyecto.id())).withSelfRel();
            return EntityModel.of(proyecto, selfLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Obtiene un proyecto por su id.
     *
     * @param id Id del proyecto.
     * @return ResponseEntity con el proyecto.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DatosDetalleProyecto>> obtenerProyectoPorId(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        DatosDetalleProyecto proyectoDTO = new DatosDetalleProyecto(proyecto);
        return ResponseEntity.ok(EntityModel.of(proyectoDTO));
    }

    /**
     * Registra un proyecto.
     *
     * @param datosRegistroProyecto Datos del proyecto a registrar.
     * @param uriComponentsBuilder  UriComponentsBuilder.
     * @return ResponseEntity con el proyecto registrado.
     */

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<EntityModel<DatosDetalleProyecto>> registrarProyecto(@RequestBody @Valid DatosRegistroProyecto datosRegistroProyecto,
                                                                               UriComponentsBuilder uriComponentsBuilder) {
        Proyecto proyecto = proyectoService.registrarProyecto(datosRegistroProyecto);
        DatosDetalleProyecto proyectoDTO = new DatosDetalleProyecto(proyecto);
        URI uri = uriComponentsBuilder.path("/proyectos/{id}").buildAndExpand(proyectoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(EntityModel.of(proyectoDTO));
    }

    @PutMapping("/modificar")
    @Transactional
    public ResponseEntity<DatosDetalleProyecto> modificarProyecto(@RequestBody @Valid DatosActualizarProyecto datosRegistroProyecto) {
        Proyecto proyecto = proyectoService.modificarProyecto(datosRegistroProyecto);
        DatosDetalleProyecto proyectoDTO = new DatosDetalleProyecto(proyecto);
        return ResponseEntity.ok(proyectoDTO);
    }


    /**
     * Elimina un proyecto.
     *
     * @param id Id del proyecto a eliminar.
     * @return ResponseEntity con el proyecto eliminado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/finalizar/{id}")
    @Transactional
    public ResponseEntity<Proyecto> finalizarProyecto(@PathVariable Long id) {
        proyectoService.finalizarProyecto(id);
        return ResponseEntity.noContent().build();
    }

}
