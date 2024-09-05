package com.proyectograduacion.PGwebONG.controller;


import com.proyectograduacion.PGwebONG.domain.voluntarios.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

//    obtener lista de voluntarios
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleVoluntario>>> listarVolunarios(Pageable pageable,
                                                                                            PagedResourcesAssembler<DatosDetalleVoluntario> pagedAssembler){
        Page<DatosDetalleVoluntario> voluntarios = voluntarioService.listarVoluntarios(pageable);

        PagedModel<EntityModel<DatosDetalleVoluntario>> pagedModel = pagedAssembler.toModel(voluntarios, voluntario ->{
            Link  selfLink = linkTo(methodOn(VoluntarioController.class).obtenerVoluntarioPorId(voluntario.id())).withSelfRel();
            Link eliminarLink = linkTo(methodOn(VoluntarioController.class).eliminarVoluntario(voluntario.id())).withRel("eliminar");
            return EntityModel.of(voluntario, selfLink, eliminarLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

//    listar voluntarios inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleVoluntario>>> listarVoluntariosInactivos(Pageable pageable,
                                                                                                     PagedResourcesAssembler<DatosDetalleVoluntario> assembler){
        Page<DatosDetalleVoluntario> voluntarios = voluntarioService.listarVoluntariosInactivos(pageable);
        PagedModel<EntityModel<DatosDetalleVoluntario>> pagedModel = assembler.toModel(voluntarios, voluntario ->{
            Link selfLink = linkTo(methodOn(VoluntarioController.class).obtenerVoluntarioPorId(voluntario.id())).withSelfRel();
            Link activarLink = linkTo(methodOn(VoluntarioController.class).activarVoluntario(voluntario.id())).withRel("activar");
            Link eliminarBDLink = linkTo(methodOn(VoluntarioController.class).eliminarVoluntarioBD(voluntario.id())).withRel("eliminarBD");
            return EntityModel.of(voluntario, selfLink, activarLink, eliminarBDLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

//    obtener voluntario por id
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DatosDetalleVoluntario>> obtenerVoluntarioPorId(@PathVariable Long id){
        Voluntario voluntario = voluntarioService.obtenerVoluntarioPorId(id);
        DatosDetalleVoluntario donadorDTO = new DatosDetalleVoluntario(voluntario);

        // Enlaces específicos para un recurso individual
        EntityModel<DatosDetalleVoluntario> entityModel = EntityModel.of(donadorDTO);
        entityModel.add(linkTo(methodOn(VoluntarioController.class).modificarVoluntario(new DatosActualizarVoluntario(voluntario.getId(),null,null,null, null,null,null,null))).withRel("modificar"));
        entityModel.add(linkTo(methodOn(VoluntarioController.class).eliminarVoluntario(id)).withRel("eliminar"));
        entityModel.add(linkTo(methodOn(VoluntarioController.class).obtenerVoluntarioPorId(id)).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }


    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleVoluntario> registrarVoluntario(@RequestBody @Valid DatosRegistroVoluntario datosRegistroVoluntario,
                                                                      UriComponentsBuilder uriBuilder){

        Voluntario voluntario = voluntarioService.registrarVoluntario(datosRegistroVoluntario);
        DatosDetalleVoluntario voluntarioDTO = new DatosDetalleVoluntario(voluntario);
        URI uri = uriBuilder.path("/voluntario/{id}").buildAndExpand(voluntario.getId()).toUri();
        return ResponseEntity.created(uri).body(voluntarioDTO);
    }

    @PutMapping("/modificar")
    public ResponseEntity<DatosDetalleVoluntario> modificarVoluntario(@RequestBody @Valid DatosActualizarVoluntario datosActualizarVoluntario){
        Voluntario voluntario = voluntarioService.modificarVoluntario(datosActualizarVoluntario);
        DatosDetalleVoluntario voluntarioDTO = new DatosDetalleVoluntario(voluntario);
        return ResponseEntity.ok(voluntarioDTO);
    }

//    eliminarcion logica
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarVoluntario(@PathVariable Long id){
        voluntarioService.eliminarVoluntario(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminarBD/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarVoluntarioBD(@PathVariable Long id){
        voluntarioService.eliminarVoluntarioBD(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/activar/{id}")
    @Transactional
    public ResponseEntity<Void> activarVoluntario(@PathVariable Long id){
        voluntarioService.activarVoluntario(id);
        return ResponseEntity.noContent().build();
    }

}
