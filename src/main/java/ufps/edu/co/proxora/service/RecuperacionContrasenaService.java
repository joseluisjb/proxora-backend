package ufps.edu.co.proxora.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class RecuperacionContrasenaService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CorreoService correoService;

    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    @Transactional
    public void solicitarRecuperacion(String correo) {
        usuarioRepository.findByCorreo(correo).ifPresent(usuario -> {
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenRecuperacionExpira(OffsetDateTime.now().plusMinutes(30));
            usuarioRepository.save(usuario);

            String link = frontendUrl + "/restablecer-contrasena?token=" + token;
            correoService.notificarRecuperacionContrasena(
                    usuario.getCorreo(),
                    usuario.getNombre(),
                    link);
        });
    }

    @Transactional
    public void restablecerContrasena(String token, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        if (usuario.getTokenRecuperacionExpira() == null ||
                OffsetDateTime.now().isAfter(usuario.getTokenRecuperacionExpira())) {
            throw new RuntimeException("Token inválido o expirado");
        }

        usuario.setContrasenaHash(passwordEncoder.encode(nuevaContrasena));
        usuario.setTokenRecuperacion(null);
        usuario.setTokenRecuperacionExpira(null);
        usuario.setActualizadoEn(OffsetDateTime.now());
        usuarioRepository.save(usuario);
    }
}
