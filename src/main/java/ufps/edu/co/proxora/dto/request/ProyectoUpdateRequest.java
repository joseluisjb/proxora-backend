package ufps.edu.co.proxora.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Size;

public record ProyectoUpdateRequest(
    @Size(max = 300) String titulo,
    String resumen,
    UUID idSemestre,
    UUID idMateria,
    Short idEstado,
    Short idVisibilidad,
    List<UUID> integrantesIds,
    List<UUID> directoresIds,
    List<UUID> lineasIds,
    List<UUID> evaluadoresIds
) {}
