package com.proyectograduacion.PGwebONG.domain.proyectos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Page<Proyecto> findByActivoTrue(Pageable pageable);

    Page<Proyecto> findByActivoFalse(Pageable pageable);

    @Query("select p.activo from Proyecto p where p.id = :idProyecto")
    Boolean findActivoById(Long idProyecto);


    long countByEstado(Estado estado);

    @Query("SELECT p FROM Proyecto p WHERE p.estado = :estado ORDER BY p.fechaFin DESC")
    Page<Proyecto> findTopByEstadoOrderByFechaFinDesc(@Param("estado") Estado estado, Pageable pageable);

    @Query("SELECT p FROM Proyecto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :term, '%')) AND p.activo = true")
    Page<Proyecto> findByProyectoContaining(@Param("term") String term, PageRequest pageRequest);

}
