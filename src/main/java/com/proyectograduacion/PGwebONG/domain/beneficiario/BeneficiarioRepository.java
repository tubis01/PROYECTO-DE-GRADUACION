package com.proyectograduacion.PGwebONG.domain.beneficiario;

import com.jayway.jsonpath.JsonPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {


    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Beneficiario b WHERE b.persona.dpi = :dpi AND b.proyecto.id = :proyecto")
    Boolean existsByPersonaAndProyecto(String dpi, Long proyecto);

    Page<Beneficiario> findByActivoTrue(Pageable pageable);
}
