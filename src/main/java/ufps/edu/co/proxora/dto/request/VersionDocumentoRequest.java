package ufps.edu.co.proxora.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VersionDocumentoRequest(
    Short idTipo,
    @NotBlank @Size(max = 50) String etiquetaVersion,
    @NotBlank String rutaS3,
    @NotBlank @Size(max = 255) String nombreArchivo,
    Long tamanoBytes,
    @Size(max = 100) String mimeType,
    @NotNull UUID idSubidoPor
) {}
