package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.proyectos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proyectos")
@SecurityRequirement(name = "bearer-key")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('DIGITADOR') or hasRole('USER')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleProyecto>>> listarProyectos(Pageable pageable,
                                                                                         PagedResourcesAssembler<DatosDetalleProyecto> assembler) {
        Page<DatosDetalleProyecto> proyectos = proyectoService.listarProyectos(pageable);

        // Convertir a PagedModel sin agregar enlaces adicionales
        PagedModel<EntityModel<DatosDetalleProyecto>> pagedModel = assembler.toModel(proyectos, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<DatosDetalleProyecto>> buscarPorDpiParcial(
            @RequestParam String term,
            @RequestParam int page,
            @RequestParam int size) {
        List<DatosDetalleProyecto> proyectos = proyectoService.buscarPorDpiParcial(term, page, size);
        return ResponseEntity.ok(proyectos);
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

        // Convertir a PagedModel sin agregar enlaces adicionales
        PagedModel<EntityModel<DatosDetalleProyecto>> pagedModel = assembler.toModel(proyectos, EntityModel::of);
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

        // Devolver la entidad sin enlaces adicionales
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

        // Retornar el proyecto registrado sin enlaces adicionales
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
     * Finaliza un proyecto.
     *
     * @param id Id del proyecto a finalizar.
     * @return ResponseEntity con el proyecto finalizado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/finalizar/{id}")
    @Transactional
    public ResponseEntity<Proyecto> finalizarProyecto(@PathVariable Long id) {
        proyectoService.finalizarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
