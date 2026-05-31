package ufps.edu.co.proxora.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
