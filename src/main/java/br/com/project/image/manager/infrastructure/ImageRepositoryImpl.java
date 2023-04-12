package br.com.project.image.manager.infrastructure;
import br.com.project.image.manager.domain.model.File;
import br.com.project.image.manager.domain.model.ImageRepository;
import br.com.project.image.manager.domain.model.NotFoundException;
import br.com.project.image.manager.infrastructure.jpa.JpaRepository;
import br.com.project.image.manager.infrastructure.s3.FileS3Impl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@Component
@AllArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final JpaRepository jpaRepository;
    private final FileS3Impl s3Repository;

    @Override
    public Long persist(MultipartFile fileDto) {
        File file = null;
        File teste = null;
        try {
            file = File.builder()
                    .fileName(fileDto.getOriginalFilename())
                    .fileType(fileDto.getContentType())
                    .build();

            s3Repository.upload(fileDto);
            teste = jpaRepository.save(file);
        } catch (Exception ex) {
            throw new InfrastructureException(ex.getMessage());
        }
        log.info("Saved image in database and s3");

        return teste.getId();
    }

    @Override
    public String download(Long id){
        var file = this.findById(id);
        return s3Repository.download(file.getFileName(), file.getFileType());
    }

    @Override
    @Transactional
    public void delete(Long id){
        var file = this.findById(id);

        jpaRepository.deleteById(id);
        s3Repository.delete(file.getFileName());

        log.info("Deleted image from S3 and Database");
    }

    @Override
    public boolean fileExistsInDb(String fileName) {
        var file = jpaRepository.findByFileName(fileName);

        return !Objects.isNull(file);
    }
    public File findById(Long id) {
        return jpaRepository.findById(id).orElseThrow(() -> new NotFoundException("File " + id + " not exists"));
    }
}
