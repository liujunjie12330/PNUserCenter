package com.pn.service.entity.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 图床配置
 */
@Getter
@Component
@PropertySource(value = "classpath:config/upload.properties")
public class MinioProperties {
    @Value("${minio.endpoint}")
    private String endPoint;
    @Value("${minio.port}")
    private String port;
    @Value("${minio.bucketName}")
    private String bucketName;

}

