package ufps.edu.co.proxora.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.VersionDocumentoRequest;
import ufps.edu.co.proxora.dto.response.VersionDocumentoResponse;
import ufps.edu.co.proxora.entity.VersionDocumento;
import ufps.edu.co.proxora.mapper.VersionDocumentoMap;
import ufps.edu.co.proxora.repository.TipoDocumentoRepository;
import ufps.edu.co.proxora.repository.UsuarioRepository;
import ufps.edu.co.proxora.repository.VersionDocumentoRepository;

@Service
@RequiredArgsConstructor
public class VersionDocumentoService {

    private final VersionDocumentoRepository versionRepository;
    private final TipoDocumentoRepository tipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoService proyectoService;
    private final VersionDocumentoMap versionMap;

    public List<VersionDocumentoResponse> findByProyecto(UUID idProyecto) {
        return versionRepository.findAllByProyectoOrderByCreadoEnDesc(proyectoService.obtenerOFallar(idProyecto))
                .stream().map(versionMap::toResponse).toList();
    }

    public VersionDocumentoResponse findById(UUID id) {
        return versionMap.toResponse(obtenerOFallar(id));
    }

    public VersionDocumentoResponse create(UUID idProyecto, VersionDocumentoRequest request) {
        VersionDocumento v = new VersionDocumento();
        v.setProyecto(proyectoService.obtenerOFallar(idProyecto));
        if (request.idTipo() != null) {
            v.setTipo(tipoRepository.findById(request.idTipo())
                    .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado")));
        }
        v.setEtiquetaVersion(request.etiquetaVersion());
        v.setRutaS3(request.rutaS3());
        v.setNombreArchivo(request.nombreArchivo());
        v.setTamanoBytes(request.tamanoBytes());
        v.setMimeType(request.mimeType());
        v.setSubidoPor(usuarioRepository.findById(request.idSubidoPor())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return versionMap.toResponse(versionRepository.save(v));
    }

    private VersionDocumento obtenerOFallar(UUID id) {
        return versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Versión de documento no encontrada"));
    }
}
