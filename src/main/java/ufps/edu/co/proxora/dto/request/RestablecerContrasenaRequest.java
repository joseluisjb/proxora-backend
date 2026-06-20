package ufps.edu.co.proxora.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestablecerContrasenaRequest(
    @NotBlank String token,
    @NotBlank @Size(min = 8) String nuevaContrasena
) {}
