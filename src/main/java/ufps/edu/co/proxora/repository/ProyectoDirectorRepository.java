package ufps.edu.co.proxora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoDirector;
import ufps.edu.co.proxora.entity.ProyectoDirectorId;

public interface ProyectoDirectorRepository extends JpaRepository<ProyectoDirector, ProyectoDirectorId> {
    List<ProyectoDirector> findAllByProyecto(Proyecto proyecto);
}
