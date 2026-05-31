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
import ufps.edu.co.proxora.dto.request.MateriaRequest;
import ufps.edu.co.proxora.dto.response.MateriaResponse;
import ufps.edu.co.proxora.service.MateriaService;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping
    public ResponseEntity<Page<MateriaResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(materiaService.findAll(pageable));
    }

    @GetMapping("/activas")
    public ResponseEntity<Page<MateriaResponse>> findAllActivas(Pageable pageable) {
        return ResponseEntity.ok(materiaService.findAllActivas(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<MateriaResponse>> findByNombre(@RequestParam String nombre, Pageable pageable) {
        return ResponseEntity.ok(materiaService.findByNombre(nombre, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(materiaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MateriaResponse> create(@Valid @RequestBody MateriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponse> update(@PathVariable UUID id,
            @Valid @RequestBody MateriaRequest request) {
        return ResponseEntity.ok(materiaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        materiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
