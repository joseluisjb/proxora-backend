package ufps.edu.co.proxora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Short> {
}
