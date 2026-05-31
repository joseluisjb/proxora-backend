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
public class ProyectoDetalleResponse {
    private UUID id;
    private String titulo;
    private String resumen;
    private String semestre;
    private String materia;
    private String estado;
    private String visibilidad;
    private List<UsuarioResumenResponse> directores;
    private List<UsuarioResumenResponse> integrantes;
    private List<LineaInvestigacionResponse> lineas;
    private List<VersionDocumentoResponse> versiones;
    private OffsetDateTime creadoEn;
}
