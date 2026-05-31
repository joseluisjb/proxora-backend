package ufps.edu.co.proxora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Short> {

    public Optional<Rol> findByNombre(String nombre);
}
