package ufps.edu.co.proxora.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MateriaRequest(
    @NotBlank @Size(max = 150) String nombre,
    @Size(max = 20) String codigo,
    Boolean activa,
    UUID creadoPor
) {}
