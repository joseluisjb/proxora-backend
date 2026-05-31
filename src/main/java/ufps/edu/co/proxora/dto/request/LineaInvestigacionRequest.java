package ufps.edu.co.proxora.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LineaInvestigacionRequest(
    @NotBlank @Size(max = 150) String nombre,
    String descripcion,
    Boolean activa,
    UUID creadoPor
) {}
