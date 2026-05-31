package ufps.edu.co.proxora.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoEvaluador;
import ufps.edu.co.proxora.entity.Usuario;

public interface ProyectoEvaluadorRepository extends JpaRepository<ProyectoEvaluador, UUID> {
    List<ProyectoEvaluador> findAllByProyecto(Proyecto proyecto);
    boolean existsByProyectoAndDocente(Proyecto proyecto, Usuario docente);
}
