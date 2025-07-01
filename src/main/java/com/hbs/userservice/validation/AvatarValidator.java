package com.hbs.userservice.validation;

import com.hbs.userservice.exception.domain.avatar.AvatarEmptyException;
import com.hbs.userservice.exception.domain.avatar.AvatarFileTypeException;
import com.hbs.userservice.exception.domain.avatar.AvatarSizeException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class AvatarValidator {

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/png");
    private static final long MAX_FILE_SIZE = 1_100_000;

    public void validateAvatar(MultipartFile file) {

        if (file.isEmpty()) {
            throw new AvatarEmptyException();
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new AvatarFileTypeException();
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".png")) {
            throw new AvatarFileTypeException();
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new AvatarSizeException();
        }
    }
}
