package br.com.project.image.manager.domain.application;

import br.com.project.image.manager.domain.model.BusinessException;
import br.com.project.image.manager.domain.model.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class FileApplication {
    private ImageRepository repository;
    public Long persistImage(MultipartFile fileDto) {
        if (repository.fileExistsInDb(fileDto.getOriginalFilename())) {
            var error = "File already exists with name -> " + fileDto.getOriginalFilename();
            log.error(error);
            throw new BusinessException(error);
        }
        return repository.persist(fileDto);
    }

    public String downloadFile(Long id) {
        return repository.download(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
