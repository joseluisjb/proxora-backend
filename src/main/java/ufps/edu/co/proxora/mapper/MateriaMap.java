package ufps.edu.co.proxora.mapper;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.response.MateriaResponse;
import ufps.edu.co.proxora.entity.Materia;

@Component
public class MateriaMap {

    public MateriaResponse toResponse(Materia materia) {
        return MateriaResponse.builder()
                .id(materia.getId())
                .nombre(materia.getNombre())
                .codigo(materia.getCodigo())
                .activa(materia.getActiva())
                .creadoPorId(materia.getCreadoPor() != null ? materia.getCreadoPor().getId() : null)
                .creadoEn(materia.getCreadoEn())
                .build();
    }
}
