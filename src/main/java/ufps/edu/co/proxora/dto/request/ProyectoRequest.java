package ufps.edu.co.proxora.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProyectoRequest(
    @NotBlank @Size(max = 300) String titulo,
    @NotBlank String resumen,
    UUID idSemestre,
    UUID idMateria,
    @NotNull Short idEstado,
    @NotNull Short idVisibilidad,
    @NotNull UUID idRegistradoPor,
    List<UUID> integrantesIds,
    List<UUID> directoresIds,
    List<UUID> lineasIds
) {}
