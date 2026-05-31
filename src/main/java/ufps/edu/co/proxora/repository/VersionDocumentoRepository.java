package ufps.edu.co.proxora.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.VersionDocumento;

public interface VersionDocumentoRepository extends JpaRepository<VersionDocumento, UUID> {
    List<VersionDocumento> findAllByProyectoOrderByCreadoEnDesc(Proyecto proyecto);
}
