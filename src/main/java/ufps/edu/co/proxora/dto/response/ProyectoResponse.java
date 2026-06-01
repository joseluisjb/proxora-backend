package ufps.edu.co.proxora.dto.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProyectoResponse {
    private UUID id;
    private String titulo;
    private String resumen;
    private String semestre;
    private String materia;
    private String estado;
    private String visibilidad;
    private UsuarioResumenResponse registradoPor;
    private List<UsuarioResumenResponse> integrantes;
    private List<UsuarioResumenResponse> directores;
    private List<LineaInvestigacionResponse> lineas;
    private List<UsuarioResumenResponse> evaluadores;
    private OffsetDateTime creadoEn;
    private OffsetDateTime actualizadoEn;
}
