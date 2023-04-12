package br.com.project.image.manager.config.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "cloud.aws")
@Getter
@Setter
public class AWSConfig {
    private String accessKey;
    private String secretKey;
    private String region;
}
