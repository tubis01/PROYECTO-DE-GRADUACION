package com.proyectograduacion.PGwebONG.domain.beneficiario;
import com.proyectograduacion.PGwebONG.domain.proyectos.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Beneficiario b WHERE b.persona.dpi = :dpi AND b.proyecto.id = :proyecto")
    Boolean existsByPersonaAndProyecto(String dpi, Long proyecto);

    Page<Beneficiario> findByActivoTrue(Pageable pageable);

    @Query("SELECT b FROM Beneficiario b WHERE b.proyecto.id = :idProyecto AND b.activo = :activo")
    List<Beneficiario> findByProyectoIdAndActivo(Long idProyecto, boolean activo);

    List<Beneficiario> findByProyecto(Proyecto proyecto);

    @Query("SELECT b FROM Beneficiario b where b.persona.dpi Like %:dpi% AND b.activo = true")
    Page<Beneficiario> findByPersonaDpiContaining(@Param("dpi") String dpi, Pageable pageable);

    @Query("SELECT b FROM Beneficiario b WHERE LOWER(b.proyecto.nombre) LIKE LOWER(CONCAT('%', :nombreProyecto, '%'))")
    Page<Beneficiario> findByProyectoNombreContaining(String nombreProyecto, PageRequest pageRequest);

    long countByProyectoIdAndActivo(Long idProyeto, boolean activo);

    long countByActivo(boolean activo);

    @Query("SELECT COUNT (b) FROM Beneficiario b WHERE MONTH(b.fechaAsignacion) = :mes")
    long countByMesDeAsignacion(int mes);
}
