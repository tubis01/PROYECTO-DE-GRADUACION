package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.personas.*;
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
@RequestMapping("/api/personas")
public class PersonaController {
    private final PersonaService personaService;
    private final PagedResourcesAssembler<DatosDetallePersona> pagedAssembler;

    public PersonaController(PersonaService personaService,
                             PagedResourcesAssembler<DatosDetallePersona> pagedAssembler) {
        this.personaService = personaService;
        this.pagedAssembler = pagedAssembler;
    }

//    obtener lista de personas
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetallePersona>>> listarPersonas(Pageable pageable) {
        Page<DatosDetallePersona> personas = personaService.listarPersonas(pageable);
        PagedModel<EntityModel<DatosDetallePersona>> pagedModel = pagedAssembler.toModel(personas);
        return ResponseEntity.ok(pagedModel);
    }

//    obtener persona por dpi

    @GetMapping("/{dpi}")
    public ResponseEntity<DatosDetallePersona> obtenerPersonaPorDPI(@PathVariable String dpi) {
        Persona persona = personaService.obtenerPersonaPorDPI(dpi);
        DatosDetallePersona personaDTO = new DatosDetallePersona(persona);
        return ResponseEntity.ok(personaDTO);
    }
//    registrar persona
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetallePersona> registrarPersona(@RequestBody @Valid DatosRegistroPersona datosRegistroPersona,
                                                                UriComponentsBuilder uriBuilder) {
        Persona persona = personaService.registrarPersona(datosRegistroPersona);
        DatosDetallePersona personaDTO = new DatosDetallePersona(persona);
        URI uri = uriBuilder.path("/api/personaz/{id}").buildAndExpand(persona.getId()).toUri();
        return ResponseEntity.created(uri).body(personaDTO);

    }

//    actualizar persona
    @PutMapping("/modificar")
    @Transactional
    public ResponseEntity<DatosDetallePersona> modificarPersona(@RequestBody @Valid DatosActualizarPersona datosActualizarPersona) {
        Persona persona = personaService.modificarPersona(datosActualizarPersona);
        DatosDetallePersona personaDTO = new DatosDetallePersona(persona);
        return ResponseEntity.ok(personaDTO);
    }

//    elimininacinoLogica
    @DeleteMapping("/eliminar/{dpi}")
    @Transactional
    public ResponseEntity<Persona> eliminarPersona(@PathVariable String dpi){
        personaService.eliminarPersona(dpi);
        return ResponseEntity.noContent().build();
    }


}
