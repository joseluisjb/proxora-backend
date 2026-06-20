package ufps.edu.co.proxora.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.EvaluacionRequest;
import ufps.edu.co.proxora.dto.request.ProyectoEvaluadorRequest;
import ufps.edu.co.proxora.dto.response.EvaluacionResponse;
import ufps.edu.co.proxora.dto.response.ProyectoEvaluadorResponse;
import ufps.edu.co.proxora.entity.Evaluacion;
import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoEvaluador;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.mapper.EvaluacionMap;
import ufps.edu.co.proxora.repository.EvaluacionRepository;
import ufps.edu.co.proxora.repository.ProyectoEvaluadorRepository;
import ufps.edu.co.proxora.repository.ProyectoIntegranteRepository;
import ufps.edu.co.proxora.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final ProyectoEvaluadorRepository evaluadorRepository;
    private final ProyectoIntegranteRepository integranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoService proyectoService;
    private final EvaluacionMap evaluacionMap;
    private final CorreoService correoService;

    public List<EvaluacionResponse> findByProyecto(UUID idProyecto) {
        return evaluacionRepository.findAllByProyectoOrderByCreadoEnDesc(proyectoService.obtenerOFallar(idProyecto))
                .stream().map(evaluacionMap::toEvaluacionResponse).toList();
    }

    public List<ProyectoEvaluadorResponse> findEvaluadoresByProyecto(UUID idProyecto) {
        return evaluadorRepository.findAllByProyecto(proyectoService.obtenerOFallar(idProyecto))
                .stream().map(evaluacionMap::toEvaluadorResponse).toList();
    }

    public EvaluacionResponse create(UUID idProyecto, EvaluacionRequest request) {
        Proyecto proyecto = proyectoService.obtenerOFallar(idProyecto);
        Usuario docente = obtenerUsuarioOFallar(request.idDocente());
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setProyecto(proyecto);
        evaluacion.setDocente(docente);
        evaluacion.setCalificacion(request.calificacion());
        evaluacion.setComentario(request.comentario());
        Evaluacion saved = evaluacionRepository.save(evaluacion);
        correoService.notificarCalificacion(integranteRepository.findAllByProyecto(proyecto), proyecto, saved);
        return evaluacionMap.toEvaluacionResponse(saved);
    }

    public ProyectoEvaluadorResponse asignarEvaluador(UUID idProyecto, ProyectoEvaluadorRequest request) {
        Proyecto proyecto = proyectoService.obtenerOFallar(idProyecto);
        Usuario docente = obtenerUsuarioOFallar(request.idDocente());
        if (evaluadorRepository.existsByProyectoAndDocente(proyecto, docente)) {
            throw new RuntimeException("El docente ya es evaluador de este proyecto");
        }
        ProyectoEvaluador evaluador = new ProyectoEvaluador();
        evaluador.setProyecto(proyecto);
        evaluador.setDocente(docente);
        evaluador.setAsignadoPor(obtenerUsuarioOFallar(request.idAsignadoPor()));
        evaluador.setCorreoEnviado(false);
        ProyectoEvaluador saved = evaluadorRepository.save(evaluador);
        correoService.notificarEvaluadorAsignado(docente, proyecto);
        saved.setCorreoEnviado(true);
        evaluadorRepository.save(saved);
        return evaluacionMap.toEvaluadorResponse(saved);
    }

    public void removerEvaluador(UUID idEvaluador) {
        if (!evaluadorRepository.existsById(idEvaluador)) {
            throw new RuntimeException("Evaluador no encontrado");
        }
        evaluadorRepository.deleteById(idEvaluador);
    }

    public long contarProyectosComoEvaluador(UUID idDocente) {
        return evaluadorRepository.countByDocente(obtenerUsuarioOFallar(idDocente));
    }

    public long contarProyectosPendientesDeCalificacion(UUID idDocente) {
        return evaluadorRepository.countPendientesByDocente(obtenerUsuarioOFallar(idDocente));
    }

    private Usuario obtenerUsuarioOFallar(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
