package com.hbs.userservice.unit.service.storage;


import com.hbs.userservice.exception.domain.storage.EmptyFileException;
import com.hbs.userservice.exception.domain.storage.StorageException;
import com.hbs.userservice.service.storage.S3StorageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3StorageServiceTest {

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private S3StorageServiceImpl s3StorageService;

    @Test
    void uploadFile_shouldPutFileAndReturnKey() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        byte[] fileContent = "dima shagahod hui sosi".getBytes();

        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn(fileContent);

        String key = s3StorageService.uploadFile(file, "test");

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        ArgumentCaptor<RequestBody> bodyCaptor = ArgumentCaptor.forClass(RequestBody.class);

        verify(s3Client).putObject(requestCaptor.capture(), bodyCaptor.capture());

        PutObjectRequest putObjectRequest = requestCaptor.getValue();
        assertEquals(key, putObjectRequest.key());
        assertEquals("text/plain", putObjectRequest.contentType());
        assertNotNull(bodyCaptor.getValue());
    }

    @Test
    void uploadFile_shouldThrowStorageException() throws Exception {
        MultipartFile file = mock(MultipartFile.class);

        when(file.getBytes()).thenThrow(new IOException("fail"));
        when(file.getOriginalFilename()).thenReturn("fail.txt");

        StorageException ex = assertThrows(StorageException.class, () -> s3StorageService.uploadFile(file, "test"));
        assertTrue(ex.getMessage().contains("fail.txt"));
    }

    @Test
    void uploadFile_shouldThrowEmptyFileException() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        assertThrows(EmptyFileException.class, () -> s3StorageService.uploadFile(file, "test"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void downloadFile_shouldReturnBytesFromS3() {
        String testS3Key = "dima";

        byte[] testS3Content = "Ia dima sosu hui".getBytes();
        ResponseBytes<GetObjectResponse> responseBytes =
                ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), testS3Content);

        when(s3Client.getObject(any(GetObjectRequest.class), any(ResponseTransformer.class)))
                .thenReturn(responseBytes);

        byte[] result = s3StorageService.downloadFile(testS3Key);

        assertArrayEquals(testS3Content, result);
    }

    @Test
    void deleteFile_shouldDeleteFile() {
        String testS3Key = "dima";

        s3StorageService.deleteFile(testS3Key);
        verify(s3Client).deleteObject(any(DeleteObjectRequest.class));

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().key(testS3Key).build();
        assertEquals(testS3Key, deleteObjectRequest.key());
    }

}
