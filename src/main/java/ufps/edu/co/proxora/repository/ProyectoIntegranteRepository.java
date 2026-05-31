package ufps.edu.co.proxora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoIntegrante;
import ufps.edu.co.proxora.entity.ProyectoIntegranteId;

public interface ProyectoIntegranteRepository extends JpaRepository<ProyectoIntegrante, ProyectoIntegranteId> {
    List<ProyectoIntegrante> findAllByProyecto(Proyecto proyecto);
    boolean existsById(ProyectoIntegranteId id);
}
