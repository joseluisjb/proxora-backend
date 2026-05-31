package ufps.edu.co.proxora.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ProyectoEvaluadorRequest(
    @NotNull UUID idDocente,
    @NotNull UUID idAsignadoPor
) {}
