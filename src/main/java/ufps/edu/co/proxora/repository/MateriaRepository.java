package ufps.edu.co.proxora.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Materia;

public interface MateriaRepository extends JpaRepository<Materia, UUID> {
    Page<Materia> findAllByActiva(Boolean activa, Pageable pageable);
    Page<Materia> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, UUID id);
}
