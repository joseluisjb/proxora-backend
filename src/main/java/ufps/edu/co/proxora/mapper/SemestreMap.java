package ufps.edu.co.proxora.mapper;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.request.SemestreRequest;
import ufps.edu.co.proxora.dto.response.SemestreResponse;
import ufps.edu.co.proxora.entity.Semestre;

@Component
public class SemestreMap {

    public SemestreResponse toResponse(Semestre semestre) {
        return SemestreResponse.builder()
                .id(semestre.getId())
                .nombre(semestre.getNombre())
                .activo(semestre.getActivo())
                .creadoEn(semestre.getCreadoEn())
                .build();
    }

    public Semestre toEntity(SemestreRequest request) {
        Semestre s = new Semestre();
        s.setNombre(request.nombre());
        s.setActivo(request.activo() != null ? request.activo() : true);
        return s;
    }

    public void updateEntity(Semestre semestre, SemestreRequest request) {
        semestre.setNombre(request.nombre());
        if (request.activo() != null) semestre.setActivo(request.activo());
    }
}
