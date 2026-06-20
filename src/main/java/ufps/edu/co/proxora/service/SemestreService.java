package ufps.edu.co.proxora.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.SemestreRequest;
import ufps.edu.co.proxora.dto.response.SemestreResponse;
import ufps.edu.co.proxora.entity.Semestre;
import ufps.edu.co.proxora.exception.ResourceNotFoundException;
import ufps.edu.co.proxora.mapper.SemestreMap;
import ufps.edu.co.proxora.repository.SemestreRepository;

@Service
@RequiredArgsConstructor
public class SemestreService {

    private final SemestreRepository semestreRepository;
    private final SemestreMap semestreMap;

    public Page<SemestreResponse> findAll(Pageable pageable) {
        return semestreRepository.findAll(pageable).map(semestreMap::toResponse);
    }

    public Page<SemestreResponse> findAllActivos(Pageable pageable) {
        return semestreRepository.findAllByActivo(true, pageable).map(semestreMap::toResponse);
    }

    public SemestreResponse findById(UUID id) {
        return semestreMap.toResponse(obtenerOFallar(id));
    }

    public SemestreResponse create(SemestreRequest request) {
        return semestreMap.toResponse(semestreRepository.save(semestreMap.toEntity(request)));
    }

    public SemestreResponse update(UUID id, SemestreRequest request) {
        Semestre semestre = obtenerOFallar(id);
        semestreMap.updateEntity(semestre, request);
        return semestreMap.toResponse(semestreRepository.save(semestre));
    }

    public void delete(UUID id) {
        obtenerOFallar(id);
        semestreRepository.deleteById(id);
    }

    public Semestre obtenerOFallar(UUID id) {
        return semestreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semestre no encontrado"));
    }
}
