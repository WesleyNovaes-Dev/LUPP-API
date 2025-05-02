package com.example.LUPP_API.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.LUPP_API.config.AWSProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AWSS3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public AWSS3Service(AmazonS3 amazonS3, AWSProperties awsProperties) {
        this.amazonS3 = amazonS3;
        this.bucketName = awsProperties.getBucketName();
    }

    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar arquivo para o S3", e);
        }
    }
}