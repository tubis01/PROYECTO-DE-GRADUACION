package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.donadores.*;
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
@RequestMapping("/donadores")
@SecurityRequirement(name = "bearer-key")
public class DonadorController {

    private final DonadorService donadorService;

    public DonadorController(DonadorService donadorService) {
        this.donadorService = donadorService;
    }

    // Obtener lista de donadores
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleDonador>>> listarDonadores(Pageable pageable,
                                                                                        PagedResourcesAssembler<DatosDetalleDonador> assembler) {
        Page<DatosDetalleDonador> donadores = donadorService.listarDonadores(pageable);

        // Convertir a PagedModel sin enlaces adicionales
        PagedModel<EntityModel<DatosDetalleDonador>> pagedModel = assembler.toModel(donadores, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    // Listar donadores inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleDonador>>> listarDonadoresInactivos(Pageable pageable,
                                                                                                 PagedResourcesAssembler<DatosDetalleDonador> assembler) {
        Page<DatosDetalleDonador> donadores = donadorService.listarDonadoresInactivos(pageable);

        // Convertir a PagedModel sin enlaces adicionales
        PagedModel<EntityModel<DatosDetalleDonador>> pagedModel = assembler.toModel(donadores, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    // Obtener donador por id
    @GetMapping("/detalle/{id}")
    public ResponseEntity<EntityModel<DatosDetalleDonador>> obtenerDonadorPorId(@PathVariable Long id) {
        Donador donador = donadorService.obtenerDonadorPorId(id);
        DatosDetalleDonador donadorDTO = new DatosDetalleDonador(donador);

        // Devolver la entidad sin enlaces adicionales
        EntityModel<DatosDetalleDonador> entityModel = EntityModel.of(donadorDTO);
        return ResponseEntity.ok(entityModel);
    }

    // Registrar un nuevo donador
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleDonador> registrarDonador(@RequestBody @Valid DatosRegistroDonador datosRegistroDonador,
                                                                UriComponentsBuilder uriBuilder) {
        Donador donador = donadorService.registrarDonador(datosRegistroDonador);
        DatosDetalleDonador donadorDTO = new DatosDetalleDonador(donador);

        // Construir la URI del nuevo recurso
        URI uri = uriBuilder.path("/donadores/{id}").buildAndExpand(donador.getId()).toUri();
        return ResponseEntity.created(uri).body(donadorDTO);
    }

    // Modificar los datos de un donador
    @PutMapping("/modificar")
    @Transactional
    public ResponseEntity<DatosDetalleDonador> modificarDonador(@RequestBody @Valid DatosActualizarDonador datosActualizarDonador) {
        Donador donador = donadorService.modificarDonador(datosActualizarDonador);
        DatosDetalleDonador donadorDTO = new DatosDetalleDonador(donador);
        return ResponseEntity.ok(donadorDTO);
    }

    // Eliminación lógica de un donador
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Donador> eliminarDonador(@PathVariable Long id) {
        donadorService.eliminarDonador(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminación física de un donador
    @DeleteMapping("/eliminarBD/{id}")
    @Transactional
    public ResponseEntity<Donador> eliminarDonadorBD(@PathVariable Long id) {
        donadorService.eliminarDonadorBD(id);
        return ResponseEntity.noContent().build();
    }

    // Activar un donador
    @PutMapping("/activar/{id}")
    @Transactional
    public ResponseEntity<Donador> activarDonador(@PathVariable Long id) {
        donadorService.activarDonador(id);
        return ResponseEntity.noContent().build();
    }
}
