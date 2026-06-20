package ufps.edu.co.proxora.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.dto.request.VersionDocumentoRequest;
import ufps.edu.co.proxora.dto.response.VersionDocumentoResponse;
import ufps.edu.co.proxora.entity.VersionDocumento;
import ufps.edu.co.proxora.exception.ResourceNotFoundException;
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
    private final S3Service s3Service;
    private final VersionDocumentoMap versionMap;

    public record DescargaResult(byte[] bytes, String nombreArchivo, String mimeType) {}

    public List<VersionDocumentoResponse> findByProyecto(UUID idProyecto) {
        return versionRepository.findAllByProyectoOrderByCreadoEnDesc(proyectoService.obtenerOFallar(idProyecto))
                .stream().map(versionMap::toResponse).toList();
    }

    public VersionDocumentoResponse findById(UUID id) {
        return versionMap.toResponse(obtenerOFallar(id));
    }

    @Transactional
    public VersionDocumentoResponse create(UUID idProyecto, VersionDocumentoRequest request) {
        VersionDocumento v = new VersionDocumento();
        v.setProyecto(proyectoService.obtenerOFallar(idProyecto));
        mapRequestToEntity(request, v);
        return versionMap.toResponse(versionRepository.save(v));
    }

    @Transactional
    public VersionDocumentoResponse update(UUID id, VersionDocumentoRequest request) {
        VersionDocumento v = obtenerOFallar(id);
        mapRequestToEntity(request, v);
        return versionMap.toResponse(versionRepository.save(v));
    }

    @Transactional
    public void delete(UUID id) {
        obtenerOFallar(id);
        versionRepository.deleteById(id);
    }

    @Transactional
    public VersionDocumentoResponse uploadDocumento(UUID idProyecto, MultipartFile archivo,
            Short idTipo, String etiquetaVersion, UUID idSubidoPor) {
        S3Service.UploadResult result = s3Service.uploadFile(archivo);
        VersionDocumento v = new VersionDocumento();
        v.setProyecto(proyectoService.obtenerOFallar(idProyecto));
        v.setTipo(idTipo != null
                ? tipoRepository.findById(idTipo)
                        .orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado"))
                : null);
        v.setEtiquetaVersion(etiquetaVersion);
        v.setRutaS3(result.keyfile());
        v.setNombreArchivo(archivo.getOriginalFilename());
        v.setTamanoBytes(archivo.getSize());
        v.setMimeType(archivo.getContentType());
        v.setSubidoPor(usuarioRepository.findById(idSubidoPor)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado")));
        return versionMap.toResponse(versionRepository.save(v));
    }

    public DescargaResult downloadDocumento(UUID id) {
        VersionDocumento v = obtenerOFallar(id);
        try {
            byte[] bytes = s3Service.downloadDocument(id);
            return new DescargaResult(bytes, v.getNombreArchivo(), v.getMimeType());
        } catch (NoSuchKeyException e) {
            throw new ResourceNotFoundException("El archivo no existe en el almacenamiento. Key: " + v.getRutaS3());
        }
    }

    private void mapRequestToEntity(VersionDocumentoRequest request, VersionDocumento v) {
        v.setTipo(request.idTipo() != null
                ? tipoRepository.findById(request.idTipo())
                        .orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado"))
                : null);
        v.setEtiquetaVersion(request.etiquetaVersion());
        v.setRutaS3(request.rutaS3());
        v.setNombreArchivo(request.nombreArchivo());
        v.setTamanoBytes(request.tamanoBytes());
        v.setMimeType(request.mimeType());
        v.setSubidoPor(usuarioRepository.findById(request.idSubidoPor())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado")));
    }

    private VersionDocumento obtenerOFallar(UUID id) {
        return versionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Versión de documento no encontrada"));
    }
}
