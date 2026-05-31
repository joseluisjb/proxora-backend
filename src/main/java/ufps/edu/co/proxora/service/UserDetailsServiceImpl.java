package ufps.edu.co.proxora.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        if (!usuario.getActivo()) {
            throw new DisabledException("Usuario inactivo");
        }

        return org.springframework.security.core.userdetails.User.builder()
            .username(usuario.getCorreo())
            .password(usuario.getContrasenaHash())
            .roles(usuario.getRol().getNombre().toUpperCase())
            // ADMINISTRADOR, DOCENTE, ESTUDIANTE, INVITADO
            .build();
    }
}
