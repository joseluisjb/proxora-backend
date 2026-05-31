package ufps.edu.co.proxora.mapper;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.response.EvaluacionResponse;
import ufps.edu.co.proxora.dto.response.ProyectoEvaluadorResponse;
import ufps.edu.co.proxora.dto.response.UsuarioResumenResponse;
import ufps.edu.co.proxora.entity.Evaluacion;
import ufps.edu.co.proxora.entity.ProyectoEvaluador;
import ufps.edu.co.proxora.entity.Usuario;

@Component
public class EvaluacionMap {

    public EvaluacionResponse toEvaluacionResponse(Evaluacion e) {
        return EvaluacionResponse.builder()
                .id(e.getId())
                .idProyecto(e.getProyecto().getId())
                .docente(toResumen(e.getDocente()))
                .calificacion(e.getCalificacion())
                .comentario(e.getComentario())
                .creadoEn(e.getCreadoEn())
                .build();
    }

    public ProyectoEvaluadorResponse toEvaluadorResponse(ProyectoEvaluador pe) {
        return ProyectoEvaluadorResponse.builder()
                .id(pe.getId())
                .idProyecto(pe.getProyecto().getId())
                .docente(toResumen(pe.getDocente()))
                .asignadoPor(toResumen(pe.getAsignadoPor()))
                .correoEnviado(pe.getCorreoEnviado())
                .creadoEn(pe.getCreadoEn())
                .build();
    }

    private UsuarioResumenResponse toResumen(Usuario u) {
        return UsuarioResumenResponse.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .correo(u.getCorreo())
                .build();
    }
}
