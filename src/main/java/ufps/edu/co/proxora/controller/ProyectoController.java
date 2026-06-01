package ufps.edu.co.proxora.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.ProyectoRequest;
import ufps.edu.co.proxora.dto.response.ProyectoDetalleResponse;
import ufps.edu.co.proxora.dto.response.ProyectoResponse;
import ufps.edu.co.proxora.service.ProyectoService;
import ufps.edu.co.proxora.service.VersionDocumentoService;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final VersionDocumentoService versionService;

    @GetMapping("/lista")
    public ResponseEntity<List<ProyectoResponse>> findAll() {
        return ResponseEntity.ok(proyectoService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<ProyectoResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(proyectoService.findAll(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProyectoResponse>> findByTitulo(@RequestParam String titulo, Pageable pageable) {
        return ResponseEntity.ok(proyectoService.findByTitulo(titulo, pageable));
    }

    @GetMapping("/semestre/{idSemestre}")
    public ResponseEntity<Page<ProyectoResponse>> findBySemestre(@PathVariable UUID idSemestre, Pageable pageable) {
        return ResponseEntity.ok(proyectoService.findBySemestre(idSemestre, pageable));
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<Page<ProyectoResponse>> findByEstado(@PathVariable Short idEstado, Pageable pageable) {
        return ResponseEntity.ok(proyectoService.findByEstado(idEstado, pageable));
    }

    @GetMapping("/materia/{idMateria}")
    public ResponseEntity<Page<ProyectoResponse>> findByMateria(@PathVariable UUID idMateria, Pageable pageable) {
        return ResponseEntity.ok(proyectoService.findByMateria(idMateria, pageable));
    }

    @GetMapping("/integrante/{idUsuario}")
    public ResponseEntity<Page<ProyectoResponse>> findByIntegrante(@PathVariable UUID idUsuario, Pageable pageable) {
        return ResponseEntity.ok(proyectoService.findByIntegrante(idUsuario, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ProyectoDetalleResponse> findDetalle(@PathVariable UUID id) {
        return ResponseEntity.ok(proyectoService.findDetalle(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProyectoResponse> create(
            @RequestPart("datos") @Valid ProyectoRequest request,
            @RequestPart("archivo") MultipartFile archivo,
            @RequestParam("etiquetaVersion") String etiquetaVersion,
            @RequestParam(value = "idTipo", required = false) Short idTipo) {
        ProyectoResponse proyecto = proyectoService.create(request);
        versionService.uploadDocumento(proyecto.getId(), archivo, idTipo, etiquetaVersion, request.idRegistradoPor());
        return ResponseEntity.status(HttpStatus.CREATED).body(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> update(@PathVariable UUID id,
            @Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.ok(proyectoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Relaciones ──────────────────────────────────────────

    @PostMapping("/{id}/integrantes/{idUsuario}")
    public ResponseEntity<Void> addIntegrante(@PathVariable UUID id, @PathVariable UUID idUsuario) {
        proyectoService.addIntegrante(id, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/integrantes/{idUsuario}")
    public ResponseEntity<Void> removeIntegrante(@PathVariable UUID id, @PathVariable UUID idUsuario) {
        proyectoService.removeIntegrante(id, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/directores/{idDocente}")
    public ResponseEntity<Void> addDirector(@PathVariable UUID id, @PathVariable UUID idDocente) {
        proyectoService.addDirector(id, idDocente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/directores/{idDocente}")
    public ResponseEntity<Void> removeDirector(@PathVariable UUID id, @PathVariable UUID idDocente) {
        proyectoService.removeDirector(id, idDocente);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/lineas/{idLinea}")
    public ResponseEntity<Void> addLinea(@PathVariable UUID id, @PathVariable UUID idLinea) {
        proyectoService.addLinea(id, idLinea);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/lineas/{idLinea}")
    public ResponseEntity<Void> removeLinea(@PathVariable UUID id, @PathVariable UUID idLinea) {
        proyectoService.removeLinea(id, idLinea);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<ProyectoResponse> cambiarEstado(@PathVariable UUID id, @PathVariable Short idEstado) {
        return ResponseEntity.ok(proyectoService.cambiarEstado(id, idEstado));
    }

    @PatchMapping("/{id}/visibilidad/{idVisibilidad}")
    public ResponseEntity<ProyectoResponse> cambiarVisibilidad(@PathVariable UUID id,
            @PathVariable Short idVisibilidad) {
        return ResponseEntity.ok(proyectoService.cambiarVisibilidad(id, idVisibilidad));
    }
}
