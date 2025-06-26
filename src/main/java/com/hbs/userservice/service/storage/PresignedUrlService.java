package com.hbs.userservice.service.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PresignedUrlService implements PresignedUrlProvider {

    private final S3Presigner s3Presigner;

    @Value("${storage.s3.bucket}")
    private String bucket;


    @Override
    public URL generatePresignedUrl(String s3Key, Duration expiration) {
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(b -> b.bucket(bucket).key(s3Key))
                .signatureDuration(expiration)
                .build();

        PresignedGetObjectRequest getObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        return getObjectRequest.url();
    }
}
