package ufps.edu.co.proxora.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.EvaluacionRequest;
import ufps.edu.co.proxora.dto.request.ProyectoEvaluadorRequest;
import ufps.edu.co.proxora.dto.response.EvaluacionResponse;
import ufps.edu.co.proxora.dto.response.ProyectoEvaluadorResponse;
import ufps.edu.co.proxora.service.EvaluacionService;

@RestController
@RequestMapping("/api/proyectos/{idProyecto}/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @GetMapping
    public ResponseEntity<List<EvaluacionResponse>> findByProyecto(@PathVariable UUID idProyecto) {
        return ResponseEntity.ok(evaluacionService.findByProyecto(idProyecto));
    }

    @PostMapping
    public ResponseEntity<EvaluacionResponse> create(@PathVariable UUID idProyecto,
            @Valid @RequestBody EvaluacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionService.create(idProyecto, request));
    }

    @GetMapping("/evaluadores")
    public ResponseEntity<List<ProyectoEvaluadorResponse>> findEvaluadores(@PathVariable UUID idProyecto) {
        return ResponseEntity.ok(evaluacionService.findEvaluadoresByProyecto(idProyecto));
    }

    @PostMapping("/evaluadores")
    public ResponseEntity<ProyectoEvaluadorResponse> asignarEvaluador(@PathVariable UUID idProyecto,
            @Valid @RequestBody ProyectoEvaluadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(evaluacionService.asignarEvaluador(idProyecto, request));
    }

    @DeleteMapping("/evaluadores/{idEvaluador}")
    public ResponseEntity<Void> removerEvaluador(@PathVariable UUID idProyecto,
            @PathVariable UUID idEvaluador) {
        evaluacionService.removerEvaluador(idEvaluador);
        return ResponseEntity.noContent().build();
    }
}
