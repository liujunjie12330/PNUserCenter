package com.pn.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: javadadi
 * @Time: 17:52
 * @ClassName: MinioProperties
 */
@Component
public class MinioProperties {
    @Value("${minio.endpoint}")
    private String endPoint;
    @Value("${minio.port}")
    private String port;
    @Value("${minio.bucketName}")
    private String bucketName;

    public String getEndPoint() {
        return endPoint;
    }

    public String getPort() {
        return port;
    }

    public String getBucketName() {
        return bucketName;
    }
}

