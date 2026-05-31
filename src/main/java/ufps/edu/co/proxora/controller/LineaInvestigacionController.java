package ufps.edu.co.proxora.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.LineaInvestigacionRequest;
import ufps.edu.co.proxora.dto.response.LineaInvestigacionResponse;
import ufps.edu.co.proxora.service.LineaInvestigacionService;

@RestController
@RequestMapping("/api/lineas-investigacion")
@RequiredArgsConstructor
public class LineaInvestigacionController {

    private final LineaInvestigacionService lineaService;

    @GetMapping
    public ResponseEntity<Page<LineaInvestigacionResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(lineaService.findAll(pageable));
    }

    @GetMapping("/activas")
    public ResponseEntity<Page<LineaInvestigacionResponse>> findAllActivas(Pageable pageable) {
        return ResponseEntity.ok(lineaService.findAllActivas(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<LineaInvestigacionResponse>> findByNombre(
            @RequestParam String nombre, Pageable pageable) {
        return ResponseEntity.ok(lineaService.findByNombre(nombre, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineaInvestigacionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(lineaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LineaInvestigacionResponse> create(
            @Valid @RequestBody LineaInvestigacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lineaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineaInvestigacionResponse> update(
            @PathVariable UUID id, @Valid @RequestBody LineaInvestigacionRequest request) {
        return ResponseEntity.ok(lineaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        lineaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
