package ufps.edu.co.proxora.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Evaluacion;
import ufps.edu.co.proxora.entity.Proyecto;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, UUID> {
    List<Evaluacion> findAllByProyecto(Proyecto proyecto);
}
