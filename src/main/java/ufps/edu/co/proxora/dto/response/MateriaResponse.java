package ufps.edu.co.proxora.dto.response;

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
public class MateriaResponse {
    private UUID id;
    private String nombre;
    private String codigo;
    private Boolean activa;
    private UUID creadoPorId;
    private OffsetDateTime creadoEn;
}
