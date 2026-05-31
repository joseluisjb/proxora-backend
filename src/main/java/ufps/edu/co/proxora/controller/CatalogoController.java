package ufps.edu.co.proxora.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.response.CatalogoResponse;
import ufps.edu.co.proxora.repository.EstadoProyectoRepository;
import ufps.edu.co.proxora.repository.NivelVisibilidadRepository;
import ufps.edu.co.proxora.repository.TipoDocumentoRepository;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final EstadoProyectoRepository estadoRepository;
    private final NivelVisibilidadRepository visibilidadRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    @GetMapping("/estados-proyecto")
    public ResponseEntity<List<CatalogoResponse>> estadosProyecto() {
        return ResponseEntity.ok(estadoRepository.findAll().stream()
                .map(e -> new CatalogoResponse(e.getId(), e.getNombre(), null))
                .toList());
    }

    @GetMapping("/niveles-visibilidad")
    public ResponseEntity<List<CatalogoResponse>> nivelesVisibilidad() {
        return ResponseEntity.ok(visibilidadRepository.findAll().stream()
                .map(n -> new CatalogoResponse(n.getId(), n.getNombre(), n.getDescripcion()))
                .toList());
    }

    @GetMapping("/tipos-documento")
    public ResponseEntity<List<CatalogoResponse>> tiposDocumento() {
        return ResponseEntity.ok(tipoDocumentoRepository.findAll().stream()
                .map(t -> new CatalogoResponse(t.getId(), t.getNombre(), null))
                .toList());
    }
}
