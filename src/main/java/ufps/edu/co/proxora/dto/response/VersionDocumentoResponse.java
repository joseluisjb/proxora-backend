package ufps.edu.co.proxora.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VersionDocumentoResponse {
    private UUID id;
    private UUID idProyecto;
    private String tipoDocumento;
    private String etiquetaVersion;
    private String rutaS3;
    private String nombreArchivo;
    private Long tamanoBytes;
    private String mimeType;
    private UsuarioResumenResponse subidoPor;
    private OffsetDateTime creadoEn;
}
