package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.beneficiario.*;
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
import java.util.List;

@RestController
@RequestMapping("/beneficiarios")
@SecurityRequirement(name = "bearer-key")
public class BeneficiarioController {
    private final BeneficiarioService beneficiarioService;

    public BeneficiarioController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }

    /**
     * Lista los beneficiarios.
     *
     * @param pageable Paginación.
     * @param assembler Ensamblador de recursos paginados.
     * @return ResponseEntity con la lista de beneficiarios.
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('RESPONSABLE')")
    @GetMapping("/listar")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleBeneficiario>>> listarPersonas(Pageable pageable,
                                                                                            PagedResourcesAssembler<DatosDetalleBeneficiario> assembler) {
        // Obtener la página de datos
        Page<DatosDetalleBeneficiario> beneficiarios = beneficiarioService.listarBeneficiarios(pageable);

        // Convertir a PagedModel sin agregar enlaces adicionales
        PagedModel<EntityModel<DatosDetalleBeneficiario>> pagedModel = assembler.toModel(beneficiarios, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Método que registra un beneficiario
     *
     * @param datosRegistroBeneficiario Datos del beneficiario a registrar
     * @param uriBuilder Constructor de URI
     * @return ResponseEntity con el beneficiario registrado
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('DIGITADOR')")
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleBeneficiario> registrarBeneficiario
    (@RequestBody @Valid DatosregistroBeneficiario datosRegistroBeneficiario,
     UriComponentsBuilder uriBuilder) {
        Beneficiario beneficiario = beneficiarioService.registrarBeneficiario(datosRegistroBeneficiario);
        DatosDetalleBeneficiario beneficiarioDTO = new DatosDetalleBeneficiario(beneficiario);
        URI uri = uriBuilder.path("/beneficiarios/{id}").buildAndExpand(beneficiarioDTO.id()).toUri();
        return ResponseEntity.created(uri).body(beneficiarioDTO);
    }

    @PutMapping("/modificar")
    public ResponseEntity<DatosDetalleBeneficiario> modificarBeneficiario(@RequestBody @Valid DatosModificarBeneficiario datosActualizarBeneficiario) {
        DatosDetalleBeneficiario beneficiario = beneficiarioService.modificarBeneficiario(datosActualizarBeneficiario);
        return ResponseEntity.ok(beneficiario);
    }

    /**
     * Método que obtiene un beneficiario por su id
     *
     * @param id Id del beneficiario a obtener
     * @return ResponseEntity con el beneficiario obtenido
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleBeneficiario> obtenerBeneficiarioPorId(@PathVariable Long id) {
        Beneficiario beneficiario = beneficiarioService.obtenerBeneficiarioPorId(id);
        return ResponseEntity.ok(new DatosDetalleBeneficiario(beneficiario));
    }

    /**
     * Método que busca beneficiarios por DPI parcial
     *
     * @param dpi DPI parcial del beneficiario a buscar
     * @param page Página
     * @param size Tamaño de la página
     * @return ResponseEntity con la página de beneficiarios encontrados
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('RESPONSABLE')")
    @GetMapping("/buscarDpiParcial")
    public ResponseEntity<List<DatosDetalleBeneficiario>> buscarPorDpiParcial(
            @RequestParam String dpi,
            @RequestParam int page,
            @RequestParam int size) {
        List<DatosDetalleBeneficiario> beneficiarios = beneficiarioService.buscarPorDpiParcial(dpi, page, size);
        return ResponseEntity.ok(beneficiarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscarNombreProyecto")
    public ResponseEntity<List<DatosDetalleBeneficiario>> buscarPorNombreProyecto(
            @RequestParam String nombreProyecto,
            @RequestParam int page,
            @RequestParam int limit) {
        List<DatosDetalleBeneficiario> beneficiarios = beneficiarioService.buscarPorNombreProyecto(nombreProyecto, page, limit);
        return ResponseEntity.ok(beneficiarios);
    }

    /**
     * Método para eliminar un beneficiario
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESPONSABLE')")
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Beneficiario> desactivarBeneficiario(@PathVariable Long id) {
        beneficiarioService.desactivarBeneficiario(id);
        return ResponseEntity.noContent().build();
    }
}
