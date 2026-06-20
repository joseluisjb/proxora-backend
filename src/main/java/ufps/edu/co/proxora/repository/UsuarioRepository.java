package ufps.edu.co.proxora.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ufps.edu.co.proxora.entity.Rol;
import ufps.edu.co.proxora.entity.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByTokenRecuperacion(String token);
    boolean existsByCorreo(String correo);

    Page<Usuario> findAllByRol(Rol rol, Pageable pageable);

    Page<Usuario> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido, Pageable pageable);
}
