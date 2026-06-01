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
import ufps.edu.co.proxora.dto.response.LoginResponse;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.repository.UsuarioRepository;
import ufps.edu.co.proxora.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.correo(), request.password()
            )
        );

        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
            .orElseThrow();

        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponse(
            token,
            usuario.getRol().getNombre(),
            usuario.getNombre() + " " + usuario.getApellido(),
            usuario.getId()
        ));
    }
}

