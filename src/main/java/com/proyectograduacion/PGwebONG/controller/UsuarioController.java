package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.usuarios.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Lista los usuarios activos.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de usuarios activos.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleUsuario>>> listarUsuario(Pageable pageable,
                                                                                      PagedResourcesAssembler<DatosDetalleUsuario> assembler) {
        Page<DatosDetalleUsuario> usuarios = usuarioService.listarUsuariosActivos(pageable);

        // Convertir a PagedModel sin enlaces adicionales
        PagedModel<EntityModel<DatosDetalleUsuario>> pagedModel = assembler.toModel(usuarios, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Obtiene un usuario por su id.
     *
     * @param id Id del usuario.
     * @return ResponseEntity con el usuario obtenido.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    /**
     * Lista los usuarios inactivos.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de usuarios inactivos.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listarInactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleUsuario>>> listarUsuariosInactivos(Pageable pageable,
                                                                                                PagedResourcesAssembler<DatosDetalleUsuario> assembler) {
        Page<DatosDetalleUsuario> usuarios = usuarioService.listarUsuariosInactivos(pageable);

        // Convertir a PagedModel sin enlaces adicionales
        PagedModel<EntityModel<DatosDetalleUsuario>> pagedModel = assembler.toModel(usuarios, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param datosRegistroUsuario Datos del usuario a registrar.
     * @param uriBuilder UriComponentsBuilder.
     * @return ResponseEntity con el usuario registrado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                                UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.registrarUsuario(datosRegistroUsuario);
        DatosDetalleUsuario usuarioDTO = new DatosDetalleUsuario(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioDTO.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    /**
     * Actualiza un usuario.
     *
     * @param datos Datos del usuario a actualizar.
     * @return ResponseEntity con el usuario actualizado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar")
    @Transactional
    public ResponseEntity<DatosDetalleUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datos) {
        Usuario usuario = usuarioService.actualizarUsuario(datos);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    /**
     * Deshabilita un usuario.
     *
     * @param id Id del usuario a deshabilitar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deshabilitar/{id}")
    @Transactional
    public ResponseEntity<Object> deshabilitarUsuario(@PathVariable Long id) {
        usuarioService.deshabilitarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
