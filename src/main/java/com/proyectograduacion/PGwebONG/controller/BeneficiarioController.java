package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.beneficiario.Beneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.BeneficiarioService;
import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosDetalleBeneficiario;
import com.proyectograduacion.PGwebONG.domain.beneficiario.DatosregistroBeneficiario;
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
@RequestMapping("/beneficiarios")
public class BeneficiarioController {
    private final BeneficiarioService beneficiarioService;

    public BeneficiarioController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }

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

    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleBeneficiario> registrarBeneficiario
            (@RequestBody @Valid DatosregistroBeneficiario datosRegistroBeneficiario,
             UriComponentsBuilder uriBuilder){
        Beneficiario beneficiario = beneficiarioService.registrarBeneficiario(datosRegistroBeneficiario);
        DatosDetalleBeneficiario beneficiarioDTO = new DatosDetalleBeneficiario(beneficiario);
        URI uri = uriBuilder.path("/beneficiarios/{id}").buildAndExpand(beneficiarioDTO.id()).toUri();
        return ResponseEntity.created(uri).body(beneficiarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleBeneficiario> obtenerBeneficiarioPorId(@PathVariable Long id){
        Beneficiario beneficiario = beneficiarioService.obtenerBeneficiarioPorId(id);
        return ResponseEntity.ok(new DatosDetalleBeneficiario(beneficiario));
    }

}