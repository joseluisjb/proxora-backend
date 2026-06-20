package ufps.edu.co.proxora.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoEvaluador;
import ufps.edu.co.proxora.entity.Usuario;

public interface ProyectoEvaluadorRepository extends JpaRepository<ProyectoEvaluador, UUID> {
    List<ProyectoEvaluador> findAllByProyecto(Proyecto proyecto);
    boolean existsByProyectoAndDocente(Proyecto proyecto, Usuario docente);
    long countByDocente(Usuario docente);

    @Query("SELECT COUNT(pe) FROM ProyectoEvaluador pe WHERE pe.docente = :docente AND NOT EXISTS (SELECT e FROM Evaluacion e WHERE e.proyecto = pe.proyecto AND e.docente = :docente)")
    long countPendientesByDocente(@Param("docente") Usuario docente);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ProyectoEvaluador pe WHERE pe.proyecto = :proyecto")
    void deleteAllByProyecto(@Param("proyecto") Proyecto proyecto);
}
