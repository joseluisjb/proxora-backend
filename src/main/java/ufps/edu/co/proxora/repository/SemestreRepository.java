package ufps.edu.co.proxora.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Semestre;

public interface SemestreRepository extends JpaRepository<Semestre, UUID> {
    Page<Semestre> findAllByActivo(Boolean activo, Pageable pageable);
    boolean existsByNombreIgnoreCase(String nombre);
}
