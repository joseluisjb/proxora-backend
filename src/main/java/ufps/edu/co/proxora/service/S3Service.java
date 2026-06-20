package ufps.edu.co.proxora.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import ufps.edu.co.proxora.repository.VersionDocumentoRepository;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final VersionDocumentoRepository versionDocumentoRepository;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

    @Value("${AWS_REGION}")
    private String region;

    public record UploadResult(String keyfile, String enlaceurl) {}

    public UploadResult uploadFile(MultipartFile file) {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();
            s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        } catch (software.amazon.awssdk.core.exception.SdkException e) {
            throw new RuntimeException("Error al subir archivo a S3: " + e.getMessage(), e);
        }
        String url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
        return new UploadResult(key, url);
    }

    public byte[] downloadDocument(UUID idVersion) {
        String key = versionDocumentoRepository.findById(idVersion)
                .orElseThrow(() -> new RuntimeException("Versión de documento no encontrada"))
                .getRutaS3();
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3Client.getObjectAsBytes(getRequest).asByteArray();
    }
}
