package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.usuarios.*;
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
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleUsuario>>> listarUsuario(Pageable pageable,
                                                                                      PagedResourcesAssembler<DatosDetalleUsuario> assembler) {
        Page<DatosDetalleUsuario> usuarios = usuarioService.listarUsuariosActivos(pageable);

        PagedModel<EntityModel<DatosDetalleUsuario>> pagedModel = assembler.toModel(usuarios, usuario -> {
            Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.id())).withSelfRel();
            return EntityModel.of(usuario, selfLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

    /*
    obtener usuario por id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuario> obtenerUsuarioPorId(Long id){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    /* '
    * Método que lista los usuarios inactivos
     */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listarInactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleUsuario>>> listarUsuariosInactivos(Pageable pageable,
                                                                                      PagedResourcesAssembler<DatosDetalleUsuario> assembler) {
        Page<DatosDetalleUsuario> usuarios = usuarioService.listarUsuariosInactivos(pageable);

        PagedModel<EntityModel<DatosDetalleUsuario>> pagedModel = assembler.toModel(usuarios, usuario -> {
            Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.id())).withSelfRel();
            return EntityModel.of(usuario, selfLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

    /*
    metodo para registrar usuario
     */

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                               UriComponentsBuilder uriBuilder){
        Usuario usuario = usuarioService.registrarUsuario(datosRegistroUsuario);
        DatosDetalleUsuario usuarioDTO = new DatosDetalleUsuario(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioDTO.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }


    /*
    metodo para actualizar usuario
     */

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar")
    public ResponseEntity<DatosDetalleUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datos){
        Usuario usuario = usuarioService.actualizarUsuario(datos);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    /*
    metodo para deshabilitar usuario
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deshabilitar/{id}")
    @Transactional
    public ResponseEntity<Object> deshabilitarUsuario(@PathVariable Long id){
        usuarioService.deshabilitarUsuario(id);
        return ResponseEntity.noContent().build();
    }



}
