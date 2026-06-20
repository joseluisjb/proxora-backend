package ufps.edu.co.proxora.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.LineaInvestigacion;

public interface LineaInvestigacionRepository extends JpaRepository<LineaInvestigacion, UUID> {

    Page<LineaInvestigacion> findAllByActiva(Boolean activa, Pageable pageable);

    Page<LineaInvestigacion> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, UUID id);
}
