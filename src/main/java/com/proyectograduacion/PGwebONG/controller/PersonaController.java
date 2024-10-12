package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.personas.*;
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
@RequestMapping("/api/personas")
public class PersonaController {
    private final PersonaService personaService;


    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

//    obtener lista de personas
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetallePersona>>> listarPersonas(Pageable pageable,
                                                                                       PagedResourcesAssembler<DatosDetallePersona> assembler) {
        // Obtener la página de datos
        Page<DatosDetallePersona> personas = personaService.listarPersonas(pageable);

        PagedModel<EntityModel<DatosDetallePersona>> pagedModel = assembler.toModel(personas, persona -> {
            Link selfLink = linkTo(methodOn(PersonaController.class).obtenerPersonaPorDPI(persona.DPI())).withSelfRel();
            Link eliminarLink = linkTo(methodOn(PersonaController.class).eliminarPersona(persona.DPI())).withRel("eliminar");
            return EntityModel.of(persona, selfLink, eliminarLink);
        });
        return ResponseEntity.ok(pagedModel);

    }

    //    listar personas inactivas
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetallePersona>>> listarPersonasInactivas(Pageable pageable,
                                                                                                PagedResourcesAssembler<DatosDetallePersona> assembler){
        Page<DatosDetallePersona> personas = personaService.listarPersonasInactivas(pageable);
        PagedModel<EntityModel<DatosDetallePersona>> pagedModel = assembler.toModel(personas, persona -> {
            Link selfLink = linkTo(methodOn(PersonaController.class).obtenerPersonaPorDPI(persona.DPI())).withSelfRel();
            Link activarLink = linkTo(methodOn(PersonaController.class).activarPersona(persona.DPI())).withRel("activar");
            Link eliminarLink = linkTo(methodOn(PersonaController.class).eliminarPersonaBD(persona.DPI())).withRel("elimnarDB");
            return EntityModel.of(persona, selfLink, eliminarLink,activarLink);
        });

        return ResponseEntity.ok(pagedModel);
    }

//    obtener persona por dpi

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar/{dpi}")
    public ResponseEntity<EntityModel<DatosDetallePersona>> obtenerPersonaPorDPI(@PathVariable String dpi){
        Persona persona = personaService.obtenerPersonaPorDPI(dpi);
        DatosDetallePersona personaDTO = new DatosDetallePersona(persona);
        Link selfLink = linkTo(methodOn(PersonaController.class).obtenerPersonaPorDPI(dpi)).withSelfRel();
        Link eliminarLink = linkTo(methodOn(PersonaController.class).eliminarPersona(dpi)).withRel("eliminar");
        return ResponseEntity.ok(EntityModel.of(personaDTO, selfLink, eliminarLink));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/buscarDpiParcial")
    public ResponseEntity<List<DatosDetallePersona>> buscarPorDpiParcial(
            @RequestParam String dpi,
            @RequestParam int page,
            @RequestParam int size){
        List<DatosDetallePersona> beneficiarios = personaService.buscarPorDpiParcial(dpi, page, size);
        return ResponseEntity.ok(beneficiarios);
    }



//    registrar persona
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetallePersona> registrarPersona(@RequestBody @Valid DatosRegistroPersona datosRegistroPersona,
                                                                UriComponentsBuilder uriBuilder) {
        Persona persona = personaService.registrarPersona(datosRegistroPersona);
        DatosDetallePersona personaDTO = new DatosDetallePersona(persona);
        URI uri = uriBuilder.path("/api/personas/{id}").buildAndExpand(persona.getId()).toUri();
        return ResponseEntity.created(uri).body(personaDTO);

    }

//    actualizar persona
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modificar")
    @Transactional
    public ResponseEntity<DatosDetallePersona> modificarPersona(@RequestBody @Valid DatosActualizarPersona datosActualizarPersona) {
        var persona = personaService.modificarPersona(datosActualizarPersona);
        return ResponseEntity.ok(persona);
    }

//    elimininacinoLogica
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{dpi}")
    @Transactional
    public ResponseEntity<Persona> eliminarPersona(@PathVariable String dpi){
        personaService.eliminarPersona(dpi);
        return ResponseEntity.noContent().build();
    }

//    eliminacion fisica
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminarBD/{dpi}")
    @Transactional
    public ResponseEntity<Persona> eliminarPersonaBD(@PathVariable String dpi){
        personaService.eliminarPersonaBD(dpi);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activar/{dpi}")
    @Transactional
    public ResponseEntity<Persona> activarPersona(@PathVariable String dpi) {
        personaService.activarPersona(dpi);
        return ResponseEntity.noContent().build();
    }


}
