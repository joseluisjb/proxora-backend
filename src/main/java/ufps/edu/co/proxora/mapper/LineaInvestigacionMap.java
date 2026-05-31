package ufps.edu.co.proxora.mapper;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.request.LineaInvestigacionRequest;
import ufps.edu.co.proxora.dto.response.LineaInvestigacionResponse;
import ufps.edu.co.proxora.entity.LineaInvestigacion;

@Component
public class LineaInvestigacionMap {

    public LineaInvestigacionResponse toResponse(LineaInvestigacion entity) {
        return LineaInvestigacionResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .activa(entity.getActiva())
                .creadoPor(entity.getCreadoPor())
                .creadoEn(entity.getCreadoEn())
                .build();
    }

    public LineaInvestigacion toEntity(LineaInvestigacionRequest request) {
        LineaInvestigacion entity = new LineaInvestigacion();
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setActiva(request.activa() != null ? request.activa() : true);
        entity.setCreadoPor(request.creadoPor());
        return entity;
    }

    public void updateEntity(LineaInvestigacion entity, LineaInvestigacionRequest request) {
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        if (request.activa() != null) {
            entity.setActiva(request.activa());
        }
    }
}
