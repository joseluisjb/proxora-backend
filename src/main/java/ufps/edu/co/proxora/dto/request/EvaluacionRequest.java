package ufps.edu.co.proxora.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EvaluacionRequest(
    @NotNull UUID idDocente,
    @NotNull @DecimalMin("0.0") @DecimalMax("5.0") BigDecimal calificacion,
    @NotBlank String comentario
) {}
