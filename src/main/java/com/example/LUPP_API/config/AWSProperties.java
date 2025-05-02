package com.example.LUPP_API.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
// NOTE: removemos o @Configuration aqui

@ConfigurationProperties(prefix = "aws")
@Getter @Setter
public class AWSProperties {
    /** será lido de aws.region */
    private String region;
    /** será lido de aws.bucket.name */
    private String bucketName;
}
