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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.SemestreRequest;
import ufps.edu.co.proxora.dto.response.SemestreResponse;
import ufps.edu.co.proxora.service.SemestreService;

@RestController
@RequestMapping("/api/semestres")
@RequiredArgsConstructor
public class SemestreController {

    private final SemestreService semestreService;

    @GetMapping
    public ResponseEntity<Page<SemestreResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(semestreService.findAll(pageable));
    }

    @GetMapping("/activos")
    public ResponseEntity<Page<SemestreResponse>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(semestreService.findAllActivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemestreResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(semestreService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SemestreResponse> create(@Valid @RequestBody SemestreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(semestreService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemestreResponse> update(@PathVariable UUID id,
            @Valid @RequestBody SemestreRequest request) {
        return ResponseEntity.ok(semestreService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        semestreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
