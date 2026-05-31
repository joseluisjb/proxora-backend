package ufps.edu.co.proxora.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
public class EvaluacionResponse {
    private UUID id;
    private UUID idProyecto;
    private UsuarioResumenResponse docente;
    private BigDecimal calificacion;
    private String comentario;
    private OffsetDateTime creadoEn;
}
