package com.hbs.userservice.service.avatar;

import com.hbs.userservice.dto.response.AvatarResponse;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface AvatarService {

    AvatarResponse uploadAvatar(MultipartFile file);

    URI getAvatar(Long id);

    void deleteAvatar(Long id);
}
