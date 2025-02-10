package com.pn.web.controller;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class CaptchaControllerTest {

    @Test
    public void test() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        MinioClient minioClient = MinioClient.builder()
                .endpoint("121.40.157.239",9001,false)
                .credentials("sllh", "adminsllh")
                .build();
        createBucket(minioClient);
    }

    private static void createBucket(MinioClient minioClient) throws Exception {
        boolean exists = minioClient
                .bucketExists(BucketExistsArgs.builder().bucket("miniofile").build());
        if (exists) {
            return;
        }
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("miniofile").build());
    }


}