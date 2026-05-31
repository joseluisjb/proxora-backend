package ufps.edu.co.proxora.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SemestreRequest(
    @NotBlank @Size(max = 15) @Pattern(regexp = "\\d{4}-[12]", message = "Formato inválido, use YYYY-1 o YYYY-2")
    String nombre,
    Boolean activo
) {}
