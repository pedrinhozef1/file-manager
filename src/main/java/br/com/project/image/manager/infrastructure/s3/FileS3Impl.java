package br.com.project.image.manager.infrastructure.s3;

import br.com.project.image.manager.domain.model.NotFoundException;
import br.com.project.image.manager.infrastructure.InfrastructureException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class FileS3Impl extends S3Properties implements S3Repository {
    @Autowired
    private AmazonS3 s3;

    public void upload(MultipartFile file) {
        log.error("Start upload s3");
        try {
            s3.putObject(new PutObjectRequest(super.getBucketName(), file.getOriginalFilename(), file.getInputStream(), null));
        } catch (Exception ex) {
            log.error("Error upload s3 " + ex.getMessage());
            throw new InfrastructureException("Error in s3 upload file " + ex.getMessage());
        }
        log.error("Finish upload s3");
    }

    public String download(String fileName, String fileType) {
        return this.generateUrl(fileName, fileType);
    }

    public void delete(String fileName) {
        try {
            s3.deleteObject(super.getBucketName(), fileName);
        } catch (Exception ex) {
            var error = "Erro ao deletar arquivo do S3 {} " + ex.getMessage();
            log.error(error);
            throw new InfrastructureException(error);
        }
    }

    public boolean fileExistsInS3(String fileName) {
        return s3.doesObjectExist(super.getBucketName(), fileName);
    }

    private String generateUrl(String fileName, String fileType) {
        var assignedUrl = "";
        try {
            var expiration = LocalDateTime.now().plusMinutes(5);

            var generatePresignedUrl = new GeneratePresignedUrlRequest(
                    super.getBucketName(), fileName)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration.toDate())
                    .withResponseHeaders(new ResponseHeaderOverrides().withContentType(fileType));

            assignedUrl = s3.generatePresignedUrl(generatePresignedUrl).toString();

            log.info("url: " + assignedUrl);
        } catch (Exception ex) {
            var error = "Erro ao gerar url pre assinada {} " + ex.getMessage();
            log.error(error);
            throw new InfrastructureException(error);
        }

        return assignedUrl;
    }
}
