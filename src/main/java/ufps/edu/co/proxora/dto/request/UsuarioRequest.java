package ufps.edu.co.proxora.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @NotBlank
        @Size(max = 100)
        String nombre,

        @NotBlank
        @Size(max = 100)
        String apellido,

        @NotBlank
        @Email
        @Size(max = 255)
        String correo,

        @NotBlank
        @Size(min = 8)
        String contrasena
) {}
