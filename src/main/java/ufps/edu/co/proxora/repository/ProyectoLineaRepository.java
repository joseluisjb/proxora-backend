package ufps.edu.co.proxora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoLinea;
import ufps.edu.co.proxora.entity.ProyectoLineaId;

public interface ProyectoLineaRepository extends JpaRepository<ProyectoLinea, ProyectoLineaId> {
    List<ProyectoLinea> findAllByProyecto(Proyecto proyecto);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ProyectoLinea pl WHERE pl.proyecto = :proyecto")
    void deleteAllByProyecto(@Param("proyecto") Proyecto proyecto);
}
