package ufps.edu.co.proxora.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.response.LineaInvestigacionResponse;
import ufps.edu.co.proxora.dto.response.ProyectoDetalleResponse;
import ufps.edu.co.proxora.dto.response.ProyectoResponse;
import ufps.edu.co.proxora.dto.response.UsuarioResumenResponse;
import ufps.edu.co.proxora.dto.response.VersionDocumentoResponse;
import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoDirector;
import ufps.edu.co.proxora.entity.ProyectoEvaluador;
import ufps.edu.co.proxora.entity.ProyectoIntegrante;
import ufps.edu.co.proxora.entity.ProyectoLinea;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.entity.VersionDocumento;

@Component
public class ProyectoMap {

    public ProyectoResponse toResponse(Proyecto proyecto,
                                       List<ProyectoIntegrante> integrantes,
                                       List<ProyectoDirector> directores,
                                       List<ProyectoLinea> lineas,
                                       List<ProyectoEvaluador> evaluadores) {
        return ProyectoResponse.builder()
                .id(proyecto.getId())
                .titulo(proyecto.getTitulo())
                .resumen(proyecto.getResumen())
                .semestre(proyecto.getSemestre() != null ? proyecto.getSemestre().getNombre() : null)
                .materia(proyecto.getMateria() != null ? proyecto.getMateria().getNombre() : null)
                .estado(proyecto.getEstado().getNombre())
                .visibilidad(proyecto.getVisibilidad().getNombre())
                .registradoPor(toResumen(proyecto.getRegistradoPor()))
                .integrantes(integrantes.stream().map(i -> toResumen(i.getUsuario())).toList())
                .directores(directores.stream().map(d -> toResumen(d.getDocente())).toList())
                .lineas(lineas.stream().map(l -> toLineaResponse(l.getLineaInvestigacion())).toList())
                .evaluadores(evaluadores.stream().map(e -> toResumen(e.getDocente())).toList())
                .creadoEn(proyecto.getCreadoEn())
                .actualizadoEn(proyecto.getActualizadoEn())
                .build();
    }

    private LineaInvestigacionResponse toLineaResponse(ufps.edu.co.proxora.entity.LineaInvestigacion l) {
        return LineaInvestigacionResponse.builder()
                .id(l.getId())
                .nombre(l.getNombre())
                .descripcion(l.getDescripcion())
                .activa(l.getActiva())
                .creadoPor(l.getCreadoPor())
                .creadoEn(l.getCreadoEn())
                .build();
    }

    public ProyectoDetalleResponse toDetalle(Proyecto proyecto,
                                              List<ProyectoIntegrante> integrantes,
                                              List<ProyectoDirector> directores,
                                              List<ProyectoLinea> lineas,
                                              List<ProyectoEvaluador> evaluadores,
                                              List<VersionDocumento> versiones) {
        return ProyectoDetalleResponse.builder()
                .id(proyecto.getId())
                .titulo(proyecto.getTitulo())
                .resumen(proyecto.getResumen())
                .semestre(proyecto.getSemestre() != null ? proyecto.getSemestre().getNombre() : null)
                .materia(proyecto.getMateria() != null ? proyecto.getMateria().getNombre() : null)
                .estado(proyecto.getEstado().getNombre())
                .visibilidad(proyecto.getVisibilidad().getNombre())
                .directores(directores.stream().map(d -> toResumen(d.getDocente())).toList())
                .integrantes(integrantes.stream().map(i -> toResumen(i.getUsuario())).toList())
                .lineas(lineas.stream().map(l -> toLineaResponse(l.getLineaInvestigacion())).toList())
                .evaluadores(evaluadores.stream().map(e -> toResumen(e.getDocente())).toList())
                .versiones(versiones.stream().map(this::toVersionResponse).toList())
                .creadoEn(proyecto.getCreadoEn())
                .build();
    }

    public VersionDocumentoResponse toVersionResponse(VersionDocumento v) {
        return VersionDocumentoResponse.builder()
                .id(v.getId())
                .idProyecto(v.getProyecto().getId())
                .tipoDocumento(v.getTipo() != null ? v.getTipo().getNombre() : null)
                .etiquetaVersion(v.getEtiquetaVersion())
                .rutaS3(v.getRutaS3())
                .nombreArchivo(v.getNombreArchivo())
                .tamanoBytes(v.getTamanoBytes())
                .mimeType(v.getMimeType())
                .subidoPor(toResumen(v.getSubidoPor()))
                .creadoEn(v.getCreadoEn())
                .build();
    }

    public UsuarioResumenResponse toResumen(Usuario usuario) {
        return UsuarioResumenResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .build();
    }
}
