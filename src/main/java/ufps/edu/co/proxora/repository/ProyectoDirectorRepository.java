package ufps.edu.co.proxora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoDirector;
import ufps.edu.co.proxora.entity.ProyectoDirectorId;

public interface ProyectoDirectorRepository extends JpaRepository<ProyectoDirector, ProyectoDirectorId> {
    List<ProyectoDirector> findAllByProyecto(Proyecto proyecto);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ProyectoDirector pd WHERE pd.proyecto = :proyecto")
    void deleteAllByProyecto(@Param("proyecto") Proyecto proyecto);
}
