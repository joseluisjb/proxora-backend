package ufps.edu.co.proxora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoIntegrante;
import ufps.edu.co.proxora.entity.ProyectoIntegranteId;

public interface ProyectoIntegranteRepository extends JpaRepository<ProyectoIntegrante, ProyectoIntegranteId> {
    List<ProyectoIntegrante> findAllByProyecto(Proyecto proyecto);
    boolean existsById(ProyectoIntegranteId id);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ProyectoIntegrante pi WHERE pi.proyecto = :proyecto")
    void deleteAllByProyecto(@Param("proyecto") Proyecto proyecto);
}
