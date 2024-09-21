package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.donadores.*;
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
@RequestMapping("/donadores")
public class DonadorController {

    /**
     * Servicio de donadores.
     */
    private final DonadorService donadorService;

    public DonadorController(DonadorService donadorService) {
        this.donadorService = donadorService;
    }

//    obtener lista de donadores
    /**
     * Lista los donadores.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de donadores.
     */

    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleDonador>>> listarDonadores(Pageable pageable,
                                                                                        PagedResourcesAssembler<DatosDetalleDonador> assembler) {

        Page<DatosDetalleDonador> donadores = donadorService.listarDonadores(pageable);

        PagedModel<EntityModel<DatosDetalleDonador>> pagedModel = assembler.toModel(donadores, donador ->{
            Link selfLink  = linkTo(methodOn(DonadorController.class).obtenerDonadorPorId(donador.id())).withSelfRel();
            Link eliminarLink = linkTo(methodOn(DonadorController.class).eliminarDonador(donador.id())).withRel("eliminar");
            return EntityModel.of(donador, selfLink, eliminarLink);

        });
        return ResponseEntity.ok(pagedModel);
    }

//    listar donadores inactivos
    /**
     * Lista los donadores inactivos.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de donadores inactivos.
     */

    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleDonador>>> listarDonadoresInactivos(Pageable pageable,
                                                                                                 PagedResourcesAssembler<DatosDetalleDonador> assembler) {
        Page<DatosDetalleDonador> donadores = donadorService.listarDonadoresInactivos(pageable);
        PagedModel<EntityModel<DatosDetalleDonador>> pagedModel = assembler.toModel(donadores, donador -> {
            Link selfLink = linkTo(methodOn(DonadorController.class).obtenerDonadorPorId(donador.id())).withSelfRel();
            Link activarLink = linkTo(methodOn(DonadorController.class).activarDonador(donador.id())).withRel("activar");
            Link eliminarBdLink = linkTo(methodOn(DonadorController.class).eliminarDonadorBD(donador.id())).withRel("eliminarBD");
            return EntityModel.of(donador, selfLink, activarLink, eliminarBdLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

//    obtener donador por id
    /**
     * Obtiene un donador por su identificador.
     *
     * @param id Identificador del donador a obtener.
     * @return ResponseEntity con el donador obtenido.
     */

    @GetMapping("/detalle/{id}")
    public ResponseEntity<EntityModel<DatosDetalleDonador>> obtenerDonadorPorId(@PathVariable Long id) {
        Donador donador = donadorService.obtenerDonadorPorId(id);
        DatosDetalleDonador donadorDTO = new DatosDetalleDonador(donador);

        // Enlaces específicos para un recurso individual
        EntityModel<DatosDetalleDonador> entityModel = EntityModel.of(donadorDTO);
        entityModel.add(linkTo(methodOn(DonadorController.class).obtenerDonadorPorId(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(DonadorController.class).modificarDonador(new DatosActualizarDonador(donador.getId(),null,null,null, null,null,null,null))).withRel("modificar"));
        entityModel.add(linkTo(methodOn(DonadorController.class).eliminarDonador(id)).withRel("eliminar"));

        return ResponseEntity.ok(entityModel);
    }
//    registra donador
    /**
     * Registra un nuevo donador.
     *
     * @param datosRegistroDonador Datos del donador a registrar.
     * @param uriBuilder Constructor de URI.
     * @return ResponseEntity con el donador registrado.
     */
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleDonador> registrarDonador(@RequestBody @Valid DatosRegistroDonador datosRegistroDonador,
                                                                UriComponentsBuilder uriBuilder) {

        Donador donador = donadorService.registrarDonador(datosRegistroDonador);
        DatosDetalleDonador donadorDTO = new DatosDetalleDonador(donador);
        URI uri = uriBuilder.path("/donadores/{id}").buildAndExpand(donador.getId()).toUri();
        return ResponseEntity.created(uri).body(donadorDTO);
    }

//    modifica donador
    /**
     * Modifica los datos de un donador.
     *
     * @param datosActualizarDonador Datos del donador a modificar.
     * @return ResponseEntity con el donador modificado.
     */

    @PutMapping("/modificar")
    @Transactional
    public ResponseEntity<DatosDetalleDonador> modificarDonador(@RequestBody @Valid DatosActualizarDonador datosActualizarDonador) {
        Donador donador = donadorService.modificarDonador(datosActualizarDonador);
        DatosDetalleDonador donadorDTO = new DatosDetalleDonador(donador);
        return ResponseEntity.ok(donadorDTO);
    }

//    eliminacion logica
    /**
     * Elimina lógicamente un donador.
     *
     * @param id Identificador del donador a eliminar.
     * @return ResponseEntity con el donador eliminado.
     */

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Donador> eliminarDonador(@PathVariable Long id) {
        donadorService.eliminarDonador(id);
        return ResponseEntity.noContent().build();
    }

//    elimiinacion fisicia
    /**
     * Elimina físicamente un donador.
     *
     * @param id Identificador del donador a eliminar.
     * @return ResponseEntity con el donador eliminado.
     */

    @DeleteMapping("/eliminarBD/{id}")
    @Transactional
    public ResponseEntity<Donador> eliminarDonadorBD(@PathVariable Long id) {
        donadorService.eliminarDonadorBD(id);
        return ResponseEntity.noContent().build();
    }

//    activar donador
    /**
     * Activa un donador.
     *
     * @param id Identificador del donador a activar.
     * @return ResponseEntity con el donador activado.
     */

    @PutMapping("/activar/{id}")
    @Transactional
    public ResponseEntity<Donador> activarDonador(@PathVariable Long id) {
        donadorService.activarDonador(id);
        return ResponseEntity.noContent().build();
    }

}
