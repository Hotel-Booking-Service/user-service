package com.hbs.userservice.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface S3StorageService {

    String uploadFile(MultipartFile file, String path);

    byte[] downloadFile(String s3Key);

    void deleteFile(String s3Key);
}
