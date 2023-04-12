package br.com.project.image.manager.infrastructure.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "cloud.aws.s3")
@Getter
@Setter
public class S3Properties {
    private String uri;
    private String bucketName;
}
