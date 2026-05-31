package ufps.edu.co.proxora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoLinea;
import ufps.edu.co.proxora.entity.ProyectoLineaId;

public interface ProyectoLineaRepository extends JpaRepository<ProyectoLinea, ProyectoLineaId> {
    List<ProyectoLinea> findAllByProyecto(Proyecto proyecto);
}
