package br.com.project.image.manager.infrastructure.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3Repository {
    void upload(MultipartFile file);

    String download(String fileName, String fileType);

    void delete(String fileName);
}
