package ufps.edu.co.proxora.mapper;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.response.UsuarioResponse;
import ufps.edu.co.proxora.entity.Usuario;

@Component
public class UsuarioMap {

    public UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .activo(usuario.getActivo())
                .nombreRol(usuario.getRol().getNombre())
                .build();
    }
}
