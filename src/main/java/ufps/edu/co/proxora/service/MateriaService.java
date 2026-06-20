package ufps.edu.co.proxora.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.MateriaRequest;
import ufps.edu.co.proxora.dto.response.MateriaResponse;
import ufps.edu.co.proxora.entity.Materia;
import ufps.edu.co.proxora.exception.ResourceNotFoundException;
import ufps.edu.co.proxora.mapper.MateriaMap;
import ufps.edu.co.proxora.repository.MateriaRepository;
import ufps.edu.co.proxora.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaMap materiaMap;

    public Page<MateriaResponse> findAll(Pageable pageable) {
        return materiaRepository.findAll(pageable).map(materiaMap::toResponse);
    }

    public Page<MateriaResponse> findAllActivas(Pageable pageable) {
        return materiaRepository.findAllByActiva(true, pageable).map(materiaMap::toResponse);
    }

    public Page<MateriaResponse> findByNombre(String nombre, Pageable pageable) {
        return materiaRepository.findByNombreContainingIgnoreCase(nombre, pageable).map(materiaMap::toResponse);
    }

    public MateriaResponse findById(UUID id) {
        return materiaMap.toResponse(obtenerOFallar(id));
    }

    @Transactional
    public MateriaResponse create(MateriaRequest request) {
        Materia materia = new Materia();
        materia.setNombre(request.nombre());
        materia.setCodigo(request.codigo());
        materia.setActiva(request.activa() != null ? request.activa() : true);
        if (request.creadoPor() != null) {
            materia.setCreadoPor(usuarioRepository.findById(request.creadoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado")));
        }
        return materiaMap.toResponse(materiaRepository.save(materia));
    }

    @Transactional
    public MateriaResponse update(UUID id, MateriaRequest request) {
        Materia materia = obtenerOFallar(id);
        materia.setNombre(request.nombre());
        materia.setCodigo(request.codigo());
        if (request.activa() != null) materia.setActiva(request.activa());
        return materiaMap.toResponse(materiaRepository.save(materia));
    }

    @Transactional
    public void delete(UUID id) {
        obtenerOFallar(id);
        materiaRepository.deleteById(id);
    }

    public Materia obtenerOFallar(UUID id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
    }
}
