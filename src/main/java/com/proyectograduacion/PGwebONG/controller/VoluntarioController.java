package com.proyectograduacion.PGwebONG.controller;


import com.proyectograduacion.PGwebONG.domain.voluntarios.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/voluntarios")
@SecurityRequirement(name = "bearer-key")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    /**
     * Lista los voluntarios.
     *
     * @param pageable Paginación.
     * @param pagedAssembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de voluntarios.
     */
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleVoluntario>>> listarVolunarios(Pageable pageable,
                                                                                            PagedResourcesAssembler<DatosDetalleVoluntario> pagedAssembler) {
        Page<DatosDetalleVoluntario> voluntarios = voluntarioService.listarVoluntarios(pageable);

        // Convertir a PagedModel sin enlaces adicionales
        PagedModel<EntityModel<DatosDetalleVoluntario>> pagedModel = pagedAssembler.toModel(voluntarios, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Lista los voluntarios inactivos.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de voluntarios inactivos.
     */
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleVoluntario>>> listarVoluntariosInactivos(Pageable pageable,
                                                                                                      PagedResourcesAssembler<DatosDetalleVoluntario> assembler) {
        Page<DatosDetalleVoluntario> voluntarios = voluntarioService.listarVoluntariosInactivos(pageable);

        // Convertir a PagedModel sin enlaces adicionales
        PagedModel<EntityModel<DatosDetalleVoluntario>> pagedModel = assembler.toModel(voluntarios, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Obtiene un voluntario por su id.
     *
     * @param id Id del voluntario.
     * @return ResponseEntity con el voluntario obtenido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleVoluntario> obtenerVoluntarioPorId(@PathVariable Long id) {
        Voluntario voluntario = voluntarioService.obtenerVoluntarioPorId(id);
        DatosDetalleVoluntario voluntarioDTO = new DatosDetalleVoluntario(voluntario);
        return ResponseEntity.ok(voluntarioDTO);
    }

    /**
     * Registra un nuevo voluntario.
     *
     * @param datosRegistroVoluntario Datos del voluntario a registrar.
     * @param uriBuilder UriComponentsBuilder.
     * @return ResponseEntity con el voluntario registrado.
     */
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleVoluntario> registrarVoluntario(@RequestBody @Valid DatosRegistroVoluntario datosRegistroVoluntario,
                                                                      UriComponentsBuilder uriBuilder) {
        Voluntario voluntario = voluntarioService.registrarVoluntario(datosRegistroVoluntario);
        DatosDetalleVoluntario voluntarioDTO = new DatosDetalleVoluntario(voluntario);
        URI uri = uriBuilder.path("/voluntario/{id}").buildAndExpand(voluntario.getId()).toUri();
        return ResponseEntity.created(uri).body(voluntarioDTO);
    }

    /**
     * Modifica los datos de un voluntario.
     *
     * @param datosActualizarVoluntario Datos del voluntario a actualizar.
     * @return ResponseEntity con el voluntario actualizado.
     */
    @PutMapping("/modificar")
    public ResponseEntity<DatosDetalleVoluntario> modificarVoluntario(@RequestBody @Valid DatosActualizarVoluntario datosActualizarVoluntario) {
        Voluntario voluntario = voluntarioService.modificarVoluntario(datosActualizarVoluntario);
        DatosDetalleVoluntario voluntarioDTO = new DatosDetalleVoluntario(voluntario);
        return ResponseEntity.ok(voluntarioDTO);
    }

    /**
     * Elimina lógicamente un voluntario.
     *
     * @param id Id del voluntario a eliminar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarVoluntario(@PathVariable Long id) {
        voluntarioService.eliminarVoluntario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina físicamente un voluntario.
     *
     * @param id Id del voluntario a eliminar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @DeleteMapping("/eliminarBD/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarVoluntarioBD(@PathVariable Long id) {
        voluntarioService.eliminarVoluntarioBD(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activa un voluntario.
     *
     * @param id Id del voluntario a activar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @PutMapping("/activar/{id}")
    @Transactional
    public ResponseEntity<Void> activarVoluntario(@PathVariable Long id) {
        voluntarioService.activarVoluntario(id);
        return ResponseEntity.noContent().build();
    }
}
