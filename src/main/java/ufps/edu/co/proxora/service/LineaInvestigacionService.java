package ufps.edu.co.proxora.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.LineaInvestigacionRequest;
import ufps.edu.co.proxora.dto.response.LineaInvestigacionResponse;
import ufps.edu.co.proxora.entity.LineaInvestigacion;
import ufps.edu.co.proxora.mapper.LineaInvestigacionMap;
import ufps.edu.co.proxora.repository.LineaInvestigacionRepository;

@Service
@RequiredArgsConstructor
public class LineaInvestigacionService {

    private final LineaInvestigacionRepository lineaRepository;
    private final LineaInvestigacionMap lineaMap;

    public Page<LineaInvestigacionResponse> findAll(Pageable pageable) {
        return lineaRepository.findAll(pageable).map(lineaMap::toResponse);
    }

    public Page<LineaInvestigacionResponse> findAllActivas(Pageable pageable) {
        return lineaRepository.findAllByActiva(true, pageable).map(lineaMap::toResponse);
    }

    public Page<LineaInvestigacionResponse> findByNombre(String nombre, Pageable pageable) {
        return lineaRepository.findByNombreContainingIgnoreCase(nombre, pageable).map(lineaMap::toResponse);
    }

    public LineaInvestigacionResponse findById(UUID id) {
        return lineaMap.toResponse(obtenerOFallar(id));
    }

    public LineaInvestigacionResponse create(LineaInvestigacionRequest request) {
        LineaInvestigacion entity = lineaMap.toEntity(request);
        return lineaMap.toResponse(lineaRepository.save(entity));
    }

    public LineaInvestigacionResponse update(UUID id, LineaInvestigacionRequest request) {
        LineaInvestigacion entity = obtenerOFallar(id);
        lineaMap.updateEntity(entity, request);
        return lineaMap.toResponse(lineaRepository.save(entity));
    }

    public void delete(UUID id) {
        obtenerOFallar(id);
        lineaRepository.deleteById(id);
    }

    private LineaInvestigacion obtenerOFallar(UUID id) {
        return lineaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Línea de investigación no encontrada"));
    }
}
