package ufps.edu.co.proxora.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ufps.edu.co.proxora.dto.request.UsuarioRequest;
import ufps.edu.co.proxora.dto.response.UsuarioResponse;
import ufps.edu.co.proxora.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(request));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/rol/{nombreRol}")
    public ResponseEntity<Page<UsuarioResponse>> findAllByRol(
            @PathVariable String nombreRol, Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllByRol(nombreRol, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<UsuarioResponse>> findByNombreOrApellido(
            @RequestParam String nombre, Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findByNombreOrApellido(nombre, pageable));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable UUID id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable UUID id) {
        usuarioService.activarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/convertir-docente")
    public ResponseEntity<Void> convertirEnDocente(@PathVariable UUID id) {
        usuarioService.convertirEnDocente(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/convertir-estudiante")
    public ResponseEntity<Void> convertirEnEstudiante(@PathVariable UUID id) {
        usuarioService.convertirEnEstudiante(id);
        return ResponseEntity.noContent().build();
    }
}