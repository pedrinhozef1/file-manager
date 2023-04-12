package br.com.project.image.manager.infrastructure.s3.config;

import br.com.project.image.manager.config.aws.AWSConfig;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config extends AWSConfig {
    @Bean
    public AmazonS3 amazonS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(super.getAccessKey(), super.getSecretKey());

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(super.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
