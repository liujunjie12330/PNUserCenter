package com.pn.service.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: javadadi
 * @Time: 17:52
 * @ClassName: MinioProperties
 */
@Getter
@Component
public class MinioProperties {
    @Value("${minio.endpoint}")
    private String endPoint;
    @Value("${minio.port}")
    private String port;
    @Value("${minio.bucketName}")
    private String bucketName;

}

