package com.hbs.userservice.service.storage;

import java.net.URL;
import java.time.Duration;

public interface PresignedUrlProvider {
    URL generatePresignedUrl(String s3Key, Duration expiration);
}
