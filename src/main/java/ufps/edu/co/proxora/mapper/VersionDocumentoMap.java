package ufps.edu.co.proxora.mapper;

import org.springframework.stereotype.Component;

import ufps.edu.co.proxora.dto.response.UsuarioResumenResponse;
import ufps.edu.co.proxora.dto.response.VersionDocumentoResponse;
import ufps.edu.co.proxora.entity.VersionDocumento;

@Component
public class VersionDocumentoMap {

    public VersionDocumentoResponse toResponse(VersionDocumento v) {
        return VersionDocumentoResponse.builder()
                .id(v.getId())
                .idProyecto(v.getProyecto().getId())
                .tipoDocumento(v.getTipo() != null ? v.getTipo().getNombre() : null)
                .etiquetaVersion(v.getEtiquetaVersion())
                .rutaS3(v.getRutaS3())
                .nombreArchivo(v.getNombreArchivo())
                .tamanoBytes(v.getTamanoBytes())
                .mimeType(v.getMimeType())
                .subidoPor(UsuarioResumenResponse.builder()
                        .id(v.getSubidoPor().getId())
                        .nombre(v.getSubidoPor().getNombre())
                        .apellido(v.getSubidoPor().getApellido())
                        .correo(v.getSubidoPor().getCorreo())
                        .build())
                .creadoEn(v.getCreadoEn())
                .build();
    }
}
