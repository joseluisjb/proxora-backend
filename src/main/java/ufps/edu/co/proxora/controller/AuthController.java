package ufps.edu.co.proxora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.LoginRequest;
import ufps.edu.co.proxora.dto.request.RecuperarContrasenaRequest;
import ufps.edu.co.proxora.dto.request.RestablecerContrasenaRequest;
import ufps.edu.co.proxora.dto.response.LoginResponse;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.repository.UsuarioRepository;
import ufps.edu.co.proxora.exception.ResourceNotFoundException;
import ufps.edu.co.proxora.service.JwtService;
import ufps.edu.co.proxora.service.RecuperacionContrasenaService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final RecuperacionContrasenaService recuperacionService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.correo(), request.password()
            )
        );

        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponse(
            token,
            usuario.getRol().getNombre(),
            usuario.getNombre() + " " + usuario.getApellido(),
            usuario.getId()
        ));
    }

    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<Void> recuperarContrasena(
            @Valid @RequestBody RecuperarContrasenaRequest request) {
        recuperacionService.solicitarRecuperacion(request.correo());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<Void> restablecerContrasena(
            @Valid @RequestBody RestablecerContrasenaRequest request) {
        recuperacionService.restablecerContrasena(request.token(), request.nuevaContrasena());
        return ResponseEntity.ok().build();
    }
}

