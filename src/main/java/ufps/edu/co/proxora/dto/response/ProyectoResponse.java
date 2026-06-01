package ufps.edu.co.proxora.dto.response;

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
    private String titulo;
    private String resumen;
    private UUID idSemestre;
    private UUID idMateria;
    private Short idEstado;
    private Short idVisibilidad;
    private UUID idRegistradoPor;
    private List<UUID> integrantesIds;
    private List<UUID> directoresIds;
    private List<UUID> lineasIds;
    private List<UUID> evaluadoresIds;
}
