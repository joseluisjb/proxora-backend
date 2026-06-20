package ufps.edu.co.proxora.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.ProyectoRequest;
import ufps.edu.co.proxora.dto.request.ProyectoUpdateRequest;
import ufps.edu.co.proxora.dto.response.ProyectoDetalleResponse;
import ufps.edu.co.proxora.dto.response.ProyectoResponse;
import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoDirector;
import ufps.edu.co.proxora.entity.ProyectoDirectorId;
import ufps.edu.co.proxora.entity.ProyectoIntegrante;
import ufps.edu.co.proxora.entity.ProyectoIntegranteId;
import ufps.edu.co.proxora.entity.ProyectoLinea;
import ufps.edu.co.proxora.entity.ProyectoLineaId;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.exception.ResourceNotFoundException;
import ufps.edu.co.proxora.mapper.ProyectoMap;
import ufps.edu.co.proxora.entity.ProyectoEvaluador;
import ufps.edu.co.proxora.repository.EstadoProyectoRepository;
import ufps.edu.co.proxora.repository.LineaInvestigacionRepository;
import ufps.edu.co.proxora.repository.NivelVisibilidadRepository;
import ufps.edu.co.proxora.repository.ProyectoDirectorRepository;
import ufps.edu.co.proxora.repository.ProyectoEvaluadorRepository;
import ufps.edu.co.proxora.repository.ProyectoIntegranteRepository;
import ufps.edu.co.proxora.repository.ProyectoLineaRepository;
import ufps.edu.co.proxora.repository.ProyectoRepository;
import ufps.edu.co.proxora.repository.UsuarioRepository;
import ufps.edu.co.proxora.repository.VersionDocumentoRepository;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ProyectoIntegranteRepository integranteRepository;
    private final ProyectoDirectorRepository directorRepository;
    private final ProyectoLineaRepository lineaRepository;
    private final ProyectoEvaluadorRepository evaluadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoProyectoRepository estadoRepository;
    private final NivelVisibilidadRepository visibilidadRepository;
    private final SemestreService semestreService;
    private final MateriaService materiaService;
    private final LineaInvestigacionRepository lineaInvestigacionRepository;
    private final ProyectoMap proyectoMap;
    private final VersionDocumentoRepository versionDocumentoRepository;

    public List<ProyectoResponse> findAll() {
        return proyectoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Page<ProyectoResponse> findAll(Pageable pageable) {
        return proyectoRepository.findAll(pageable).map(this::toResponse);
    }

    public Page<ProyectoResponse> findBySemestre(UUID idSemestre, Pageable pageable) {
        return proyectoRepository.findAllBySemestre(semestreService.obtenerOFallar(idSemestre), pageable)
                .map(this::toResponse);
    }

    public Page<ProyectoResponse> findByEstado(Short idEstado, Pageable pageable) {
        return proyectoRepository.findAllByEstado(obtenerEstadoOFallar(idEstado), pageable)
                .map(this::toResponse);
    }

    public Page<ProyectoResponse> findByMateria(UUID idMateria, Pageable pageable) {
        return proyectoRepository.findAllByMateria(materiaService.obtenerOFallar(idMateria), pageable)
                .map(this::toResponse);
    }

    public Page<ProyectoResponse> findByTitulo(String palabra, Pageable pageable) {
        return proyectoRepository.findByTituloContainingIgnoreCaseOrResumenContainingIgnoreCase(palabra, palabra, pageable).map(this::toResponse);
    }

    public Page<ProyectoResponse> findByIntegrante(UUID idUsuario, Pageable pageable) {
        return proyectoRepository.findAllByIntegrante(obtenerUsuarioOFallar(idUsuario), pageable)
                .map(this::toResponse);
    }

    public Page<ProyectoResponse> findByEvaluador(UUID idUsuario, Pageable pageable) {
        return proyectoRepository.findAllByEvaluador(obtenerUsuarioOFallar(idUsuario), pageable)
                .map(this::toResponse);
    }

    public Page<ProyectoResponse> findByEvaluadorPendientes(UUID idUsuario, Pageable pageable) {
        return proyectoRepository.findAllByEvaluadorPendientes(obtenerUsuarioOFallar(idUsuario), pageable)
                .map(this::toResponse);
    }

    public ProyectoResponse findById(UUID id) {
        return toResponse(obtenerOFallar(id));
    }

    public ProyectoDetalleResponse findDetalle(UUID id) {
        Proyecto proyecto = obtenerOFallar(id);
        return proyectoMap.toDetalle(
                proyecto,
                integranteRepository.findAllByProyecto(proyecto),
                directorRepository.findAllByProyecto(proyecto),
                lineaRepository.findAllByProyecto(proyecto),
                evaluadorRepository.findAllByProyecto(proyecto),
                versionDocumentoRepository.findAllByProyectoOrderByCreadoEnDesc(proyecto));
    }

    @Transactional
    public ProyectoResponse create(ProyectoRequest request) {
        Proyecto proyecto = new Proyecto();
        mapRequestToEntity(request, proyecto);
        Proyecto saved = proyectoRepository.save(proyecto);
        saveRelaciones(saved, request);
        return toResponse(saved);
    }

    @Transactional
    public ProyectoResponse update(UUID id, ProyectoRequest request) {
        Proyecto proyecto = obtenerOFallar(id);
        mapRequestToEntity(request, proyecto);
        proyecto.setActualizadoEn(OffsetDateTime.now());
        Proyecto saved = proyectoRepository.save(proyecto);
        integranteRepository.deleteAll(integranteRepository.findAllByProyecto(saved));
        directorRepository.deleteAll(directorRepository.findAllByProyecto(saved));
        lineaRepository.deleteAll(lineaRepository.findAllByProyecto(saved));
        evaluadorRepository.deleteAll(evaluadorRepository.findAllByProyecto(saved));
        saveRelaciones(saved, request);
        return toResponse(saved);
    }

    @Transactional
    public ProyectoResponse patch(UUID id, ProyectoUpdateRequest request) {
        Proyecto proyecto = obtenerOFallar(id);
        if (request.titulo() != null) proyecto.setTitulo(request.titulo());
        if (request.resumen() != null) proyecto.setResumen(request.resumen());
        if (request.idSemestre() != null) proyecto.setSemestre(semestreService.obtenerOFallar(request.idSemestre()));
        if (request.idMateria() != null) proyecto.setMateria(materiaService.obtenerOFallar(request.idMateria()));
        if (request.idEstado() != null) proyecto.setEstado(obtenerEstadoOFallar(request.idEstado()));
        if (request.idVisibilidad() != null) proyecto.setVisibilidad(visibilidadRepository.findById(request.idVisibilidad())
                .orElseThrow(() -> new ResourceNotFoundException("Nivel de visibilidad no encontrado")));
        proyecto.setActualizadoEn(OffsetDateTime.now());
        Proyecto saved = proyectoRepository.save(proyecto);
        if (request.integrantesIds() != null) {
            integranteRepository.deleteAll(integranteRepository.findAllByProyecto(saved));
            integranteRepository.flush();
            request.integrantesIds().forEach(uid -> {
                ProyectoIntegranteId pk = new ProyectoIntegranteId(saved.getId(), uid);
                integranteRepository.save(new ProyectoIntegrante(pk, saved, obtenerUsuarioOFallar(uid)));
            });
        }
        if (request.directoresIds() != null) {
            directorRepository.deleteAll(directorRepository.findAllByProyecto(saved));
            directorRepository.flush();
            request.directoresIds().forEach(uid -> {
                ProyectoDirectorId pk = new ProyectoDirectorId(saved.getId(), uid);
                directorRepository.save(new ProyectoDirector(pk, saved, obtenerUsuarioOFallar(uid)));
            });
        }
        if (request.lineasIds() != null) {
            lineaRepository.deleteAll(lineaRepository.findAllByProyecto(saved));
            lineaRepository.flush();
            request.lineasIds().forEach(lid -> {
                ProyectoLineaId pk = new ProyectoLineaId(saved.getId(), lid);
                lineaRepository.save(new ProyectoLinea(pk, saved,
                        lineaInvestigacionRepository.findById(lid)
                                .orElseThrow(() -> new ResourceNotFoundException("Línea de investigación no encontrada"))));
            });
        }
        if (request.evaluadoresIds() != null) {
            evaluadorRepository.deleteAll(evaluadorRepository.findAllByProyecto(saved));
            evaluadorRepository.flush();
            request.evaluadoresIds().forEach(idDocente -> {
                ProyectoEvaluador evaluador = new ProyectoEvaluador();
                evaluador.setProyecto(saved);
                evaluador.setDocente(obtenerUsuarioOFallar(idDocente));
                evaluador.setAsignadoPor(saved.getRegistradoPor());
                evaluadorRepository.save(evaluador);
            });
        }
        return toResponse(saved);
    }

    @Transactional
    public void delete(UUID id) {
        obtenerOFallar(id);
        proyectoRepository.deleteById(id);
    }

    @Transactional
    public void addIntegrante(UUID idProyecto, UUID idUsuario) {
        Proyecto proyecto = obtenerOFallar(idProyecto);
        ProyectoIntegranteId pk = new ProyectoIntegranteId(idProyecto, idUsuario);
        if (!integranteRepository.existsById(pk)) {
            integranteRepository.save(new ProyectoIntegrante(pk, proyecto,
                    obtenerUsuarioOFallar(idUsuario)));
        }
    }

    @Transactional
    public void removeIntegrante(UUID idProyecto, UUID idUsuario) {
        integranteRepository.deleteById(new ProyectoIntegranteId(idProyecto, idUsuario));
    }

    @Transactional
    public void addDirector(UUID idProyecto, UUID idDocente) {
        Proyecto proyecto = obtenerOFallar(idProyecto);
        ProyectoDirectorId pk = new ProyectoDirectorId(idProyecto, idDocente);
        if (!directorRepository.existsById(pk)) {
            directorRepository.save(new ProyectoDirector(pk, proyecto,
                    obtenerUsuarioOFallar(idDocente)));
        }
    }

    @Transactional
    public void removeDirector(UUID idProyecto, UUID idDocente) {
        directorRepository.deleteById(new ProyectoDirectorId(idProyecto, idDocente));
    }

    @Transactional
    public void addLinea(UUID idProyecto, UUID idLinea) {
        Proyecto proyecto = obtenerOFallar(idProyecto);
        ProyectoLineaId pk = new ProyectoLineaId(idProyecto, idLinea);
        if (!lineaRepository.existsById(pk)) {
            lineaRepository.save(new ProyectoLinea(pk, proyecto,
                    lineaInvestigacionRepository.findById(idLinea)
                            .orElseThrow(() -> new ResourceNotFoundException("Línea de investigación no encontrada"))));
        }
    }

    @Transactional
    public void removeLinea(UUID idProyecto, UUID idLinea) {
        lineaRepository.deleteById(new ProyectoLineaId(idProyecto, idLinea));
    }

    @Transactional
    public ProyectoResponse cambiarEstado(UUID id, Short idEstado) {
        Proyecto proyecto = obtenerOFallar(id);
        proyecto.setEstado(obtenerEstadoOFallar(idEstado));
        proyecto.setActualizadoEn(OffsetDateTime.now());
        return toResponse(proyectoRepository.save(proyecto));
    }

    @Transactional
    public ProyectoResponse cambiarVisibilidad(UUID id, Short idVisibilidad) {
        Proyecto proyecto = obtenerOFallar(id);
        proyecto.setVisibilidad(visibilidadRepository.findById(idVisibilidad)
                .orElseThrow(() -> new ResourceNotFoundException("Nivel de visibilidad no encontrado")));
        proyecto.setActualizadoEn(OffsetDateTime.now());
        return toResponse(proyectoRepository.save(proyecto));
    }

    // ── helpers ──────────────────────────────────────────────

    public Proyecto obtenerOFallar(UUID id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));
    }

    private void mapRequestToEntity(ProyectoRequest request, Proyecto proyecto) {
        proyecto.setTitulo(request.titulo());
        proyecto.setResumen(request.resumen());
        proyecto.setSemestre(request.idSemestre() != null ? semestreService.obtenerOFallar(request.idSemestre()) : null);
        proyecto.setMateria(request.idMateria() != null ? materiaService.obtenerOFallar(request.idMateria()) : null);
        proyecto.setEstado(obtenerEstadoOFallar(request.idEstado()));
        proyecto.setVisibilidad(visibilidadRepository.findById(request.idVisibilidad())
                .orElseThrow(() -> new ResourceNotFoundException("Nivel de visibilidad no encontrado")));
        proyecto.setRegistradoPor(obtenerUsuarioOFallar(request.idRegistradoPor()));
    }

    private void saveRelaciones(Proyecto proyecto, ProyectoRequest request) {
        if (request.integrantesIds() != null) {
            request.integrantesIds().forEach(uid -> {
                ProyectoIntegranteId pk = new ProyectoIntegranteId(proyecto.getId(), uid);
                integranteRepository.save(new ProyectoIntegrante(pk, proyecto, obtenerUsuarioOFallar(uid)));
            });
        }
        if (request.directoresIds() != null) {
            request.directoresIds().forEach(uid -> {
                ProyectoDirectorId pk = new ProyectoDirectorId(proyecto.getId(), uid);
                directorRepository.save(new ProyectoDirector(pk, proyecto, obtenerUsuarioOFallar(uid)));
            });
        }
        if (request.lineasIds() != null) {
            request.lineasIds().forEach(lid -> {
                ProyectoLineaId pk = new ProyectoLineaId(proyecto.getId(), lid);
                lineaRepository.save(new ProyectoLinea(pk, proyecto,
                        lineaInvestigacionRepository.findById(lid)
                                .orElseThrow(() -> new ResourceNotFoundException("Línea de investigación no encontrada"))));
            });
        }
        if (request.evaluadoresIds() != null) {
            Usuario asignadoPor = obtenerUsuarioOFallar(request.idRegistradoPor());
            request.evaluadoresIds().forEach(idDocente -> {
                ProyectoEvaluador evaluador = new ProyectoEvaluador();
                evaluador.setProyecto(proyecto);
                evaluador.setDocente(obtenerUsuarioOFallar(idDocente));
                evaluador.setAsignadoPor(asignadoPor);
                evaluadorRepository.save(evaluador);
            });
        }
    }

    private ProyectoResponse toResponse(Proyecto proyecto) {
        return proyectoMap.toResponse(proyecto,
                integranteRepository.findAllByProyecto(proyecto),
                directorRepository.findAllByProyecto(proyecto),
                lineaRepository.findAllByProyecto(proyecto),
                evaluadorRepository.findAllByProyecto(proyecto));
    }

    private ufps.edu.co.proxora.entity.EstadoProyecto obtenerEstadoOFallar(Short id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de proyecto no encontrado"));
    }

    private ufps.edu.co.proxora.entity.Usuario obtenerUsuarioOFallar(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}
