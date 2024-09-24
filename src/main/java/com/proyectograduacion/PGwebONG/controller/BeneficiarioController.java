package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioService;
import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosDetalleBeneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosregistroBeneficiario;
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
@RequestMapping("/beneficiarios")
public class BeneficiarioController {
    private final BeneficiarioService beneficiarioService;

    public BeneficiarioController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
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
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleBeneficiario>>> listarPersonas(Pageable pageable,
                                                                                            PagedResourcesAssembler<DatosDetalleBeneficiario> assembler) {
        // Obtener la página de datos
        Page<DatosDetalleBeneficiario> beneficiarios = beneficiarioService.listarBeneficiarios(pageable);

        PagedModel<EntityModel<DatosDetalleBeneficiario> >pagedModel = assembler.toModel(beneficiarios, beneficiario -> {
            Link selfLink = linkTo(methodOn(BeneficiarioController.class).obtenerBeneficiarioPorId(beneficiario.id())).withSelfRel();
            return EntityModel.of(beneficiario, selfLink);
        });
        return ResponseEntity.ok(pagedModel);
    }

    /**
    * Método que registra un beneficiario
    * @param datosRegistroBeneficiario Datos del beneficiario a registrar
    * @param uriBuilder Constructor de URI
    * @return ResponseEntity con el beneficiario registrado
     */

    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleBeneficiario> registrarBeneficiario
            (@RequestBody @Valid DatosregistroBeneficiario datosRegistroBeneficiario,
             UriComponentsBuilder uriBuilder){
        Beneficiario beneficiario = beneficiarioService.registrarBeneficiario(datosRegistroBeneficiario);
        DatosDetalleBeneficiario beneficiarioDTO = new DatosDetalleBeneficiario(beneficiario);
        URI uri = uriBuilder.path("/beneficiarios/{id}").buildAndExpand(beneficiarioDTO.id()).toUri();
        return ResponseEntity.created(uri).body(beneficiarioDTO);
    }


    /**
     * Método que obtiene un beneficiario por su id
     * @param id Id del beneficiario a obtener
     * @return ResponseEntity con el beneficiario obtenido
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleBeneficiario> obtenerBeneficiarioPorId(@PathVariable Long id){
        Beneficiario beneficiario = beneficiarioService.obtenerBeneficiarioPorId(id);
        return ResponseEntity.ok(new DatosDetalleBeneficiario(beneficiario));
    }

    /**
     * metodo para eliminar un beneficiario
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Beneficiario> desactivarBeneficiario(@PathVariable Long id){
        beneficiarioService.desactivarBeneficiario(id);
        return ResponseEntity.noContent().build();
    }



}