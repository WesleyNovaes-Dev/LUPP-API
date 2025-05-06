package com.example.LUPP_API.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion("us-east-2") // Ou a região do seu bucket S3
                .build(); // Utiliza o DefaultAWSCredentialsProviderChain por padrão
    }
}