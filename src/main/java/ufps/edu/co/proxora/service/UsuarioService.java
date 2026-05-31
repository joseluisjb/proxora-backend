package ufps.edu.co.proxora.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufps.edu.co.proxora.dto.request.UsuarioRequest;
import ufps.edu.co.proxora.dto.response.UsuarioResponse;
import ufps.edu.co.proxora.entity.Rol;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.mapper.UsuarioMap;
import ufps.edu.co.proxora.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMap usuarioMap;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        Rol rolEstudiante = rolService.obtenerRolPorNombre("estudiante");
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setCorreo(request.correo());
        usuario.setContrasenaHash(passwordEncoder.encode(request.contrasena()));
        usuario.setRol(rolEstudiante);
        usuario.setActivo(true);
        return usuarioMap.toUsuarioResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse findById(UUID id) {
        return usuarioMap.toUsuarioResponse(
                usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
    }

    public Page<UsuarioResponse> findAll(Pageable pageable){
        return usuarioRepository.findAll(pageable).map(usuario -> usuarioMap.toUsuarioResponse(usuario));
    }

    public Page<UsuarioResponse> findAllByRol(String nombreRol, Pageable pageable){
        Rol rol = rolService.obtenerRolPorNombre(nombreRol);
        return usuarioRepository.findAllByRol(rol, pageable).map(usuario -> usuarioMap.toUsuarioResponse(usuario));
    }

    public Page<UsuarioResponse> findByNombreOrApellido(String nommbre, Pageable pageable){
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nommbre, nommbre, pageable)
                .map(usuario -> usuarioMap.toUsuarioResponse(usuario));
    }

    public void desactivarUsuario(UUID id) {
        usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .setActivo(false);
    }

    public void activarUsuario(UUID id) {
        usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado")).setActivo(true);
    }

    public void convertirEnDocente(UUID id) {
        usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado")).setRol(
                rolService.obtenerRolPorNombre("docente"));
    }

    public void convertirEnEstudiante(UUID id) {
        usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado")).setRol(
                rolService.obtenerRolPorNombre("estudiante"));
    }
}
