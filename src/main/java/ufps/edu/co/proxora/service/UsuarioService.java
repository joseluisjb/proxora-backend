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

    @Transactional
    public void desactivarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void activarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void convertirEnDocente(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setRol(rolService.obtenerRolPorNombre("docente"));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void convertirEnEstudiante(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setRol(rolService.obtenerRolPorNombre("estudiante"));
        usuarioRepository.save(usuario);
    }
}
