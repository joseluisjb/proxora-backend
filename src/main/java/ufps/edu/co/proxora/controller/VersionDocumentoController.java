package ufps.edu.co.proxora.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/{id}")
    public ResponseEntity<VersionDocumentoResponse> update(@PathVariable UUID idProyecto,
            @PathVariable UUID id, @Valid @RequestBody VersionDocumentoRequest request) {
        return ResponseEntity.ok(versionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID idProyecto, @PathVariable UUID id) {
        versionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VersionDocumentoResponse> upload(
            @PathVariable UUID idProyecto,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("etiquetaVersion") String etiquetaVersion,
            @RequestParam(value = "idTipo", required = false) Short idTipo,
            @RequestParam("idSubidoPor") UUID idSubidoPor) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(versionService.uploadDocumento(idProyecto, archivo, idTipo, etiquetaVersion, idSubidoPor));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable UUID idProyecto, @PathVariable UUID id) {
        VersionDocumentoService.DescargaResult result = versionService.downloadDocumento(id);
        String mimeType = result.mimeType() != null ? result.mimeType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(result.nombreArchivo())
                .build());
        return ResponseEntity.ok().headers(headers).body(result.bytes());
    }
}
