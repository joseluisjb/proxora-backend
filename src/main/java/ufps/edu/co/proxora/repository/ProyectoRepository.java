package ufps.edu.co.proxora.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufps.edu.co.proxora.entity.EstadoProyecto;
import ufps.edu.co.proxora.entity.Materia;
import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.Semestre;
import ufps.edu.co.proxora.entity.Usuario;

public interface ProyectoRepository extends JpaRepository<Proyecto, UUID> {
    Page<Proyecto> findAllBySemestre(Semestre semestre, Pageable pageable);
    Page<Proyecto> findAllByEstado(EstadoProyecto estado, Pageable pageable);
    Page<Proyecto> findAllByMateria(Materia materia, Pageable pageable);
    Page<Proyecto> findAllByRegistradoPor(Usuario registradoPor, Pageable pageable);
    Page<Proyecto> findByTituloContainingIgnoreCaseOrResumenContainingIgnoreCase(String titulo, String resumen, Pageable pageable);

    @Query("SELECT pi.proyecto FROM ProyectoIntegrante pi WHERE pi.usuario = :usuario")
    Page<Proyecto> findAllByIntegrante(@Param("usuario") Usuario usuario, Pageable pageable);

    @Query(value = "SELECT pe.proyecto FROM ProyectoEvaluador pe WHERE pe.docente = :docente",
           countQuery = "SELECT COUNT(pe) FROM ProyectoEvaluador pe WHERE pe.docente = :docente")
    Page<Proyecto> findAllByEvaluador(@Param("docente") Usuario docente, Pageable pageable);

    @Query(value = "SELECT pe.proyecto FROM ProyectoEvaluador pe WHERE pe.docente = :docente AND NOT EXISTS (SELECT e FROM Evaluacion e WHERE e.proyecto = pe.proyecto AND e.docente = :docente)",
           countQuery = "SELECT COUNT(pe) FROM ProyectoEvaluador pe WHERE pe.docente = :docente AND NOT EXISTS (SELECT e FROM Evaluacion e WHERE e.proyecto = pe.proyecto AND e.docente = :docente)")
    Page<Proyecto> findAllByEvaluadorPendientes(@Param("docente") Usuario docente, Pageable pageable);
}
