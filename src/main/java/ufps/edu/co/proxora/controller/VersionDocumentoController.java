package ufps.edu.co.proxora.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.VersionDocumentoRequest;
import ufps.edu.co.proxora.dto.response.VersionDocumentoResponse;
import ufps.edu.co.proxora.service.VersionDocumentoService;

@RestController
@RequestMapping("/api/proyectos/{idProyecto}/versiones")
@RequiredArgsConstructor
public class VersionDocumentoController {

    private final VersionDocumentoService versionService;

    @GetMapping
    public ResponseEntity<List<VersionDocumentoResponse>> findByProyecto(@PathVariable UUID idProyecto) {
        return ResponseEntity.ok(versionService.findByProyecto(idProyecto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VersionDocumentoResponse> findById(@PathVariable UUID idProyecto,
            @PathVariable UUID id) {
        return ResponseEntity.ok(versionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VersionDocumentoResponse> create(@PathVariable UUID idProyecto,
            @Valid @RequestBody VersionDocumentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(versionService.create(idProyecto, request));
    }
}
