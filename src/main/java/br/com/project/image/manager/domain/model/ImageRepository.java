package br.com.project.image.manager.domain.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageRepository {
    Long persist(MultipartFile file);
    void delete(Long id);
    String download(Long id);
    boolean fileExistsInDb(String filename);
}
