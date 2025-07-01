package com.hbs.userservice.service.avatar;

import com.hbs.userservice.dto.response.AvatarResponse;
import com.hbs.userservice.mapper.AvatarMapper;
import com.hbs.userservice.model.Avatar;
import com.hbs.userservice.model.User;
import com.hbs.userservice.repository.AvatarRepository;
import com.hbs.userservice.repository.UserRepository;
import com.hbs.userservice.resolver.AvatarResolver;
import com.hbs.userservice.service.storage.PresignedUrlProvider;
import com.hbs.userservice.service.storage.S3StorageService;
import com.hbs.userservice.validation.AvatarValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final AvatarResolver avatarResolver;
    private final AvatarMapper avatarMapper;
    private final UserRepository userRepository;
    private final S3StorageService s3StorageService;
    private final PresignedUrlProvider presignedUrlProvider;
    private final AvatarValidator validator;

    private static final String AVATAR_PATH = "users/avatars";

    @Value("${storage.s3.url-expiration-seconds:900}")
    private Long urlExpirationSeconds;

    @Override
    @Transactional
    public AvatarResponse uploadAvatar(MultipartFile file) {
        log.info("Starting avatar upload, originalFilename={}", file.getOriginalFilename());
        validator.validateAvatar(file);

        String s3Key = s3StorageService.uploadFile(file, AVATAR_PATH);

        log.debug("File uploaded to S3, s3Key={}", s3Key);
        Avatar avatar = new Avatar();
        avatar.setS3Key(s3Key);
        avatarRepository.save(avatar);
        log.info("Avatar uploaded successfully");
        return avatarMapper.toAvatarResponse(avatar);
    }

    @Override
    @Transactional(readOnly = true)
    public URI getAvatar(Long id) {
        log.debug("Fetching avatar for ID {}", id);
        Avatar avatar = avatarResolver.resolveById(id);
        URI uri = URI.create(presignedUrlProvider.generatePresignedUrl(
                avatar.getS3Key(), Duration.ofSeconds(urlExpirationSeconds)).toString());

        log.info("Generated pre-signed URL for avatarId={}, s3Key={}", id, avatar.getS3Key());
        return uri;
    }

    @Override
    @Transactional
    public void deleteAvatar(Long id) {
        log.info("Request to delete avatar, avatarId={}", id);

        Avatar avatar = avatarResolver.resolveById(id);

        Optional<User> userOpt = userRepository.findByAvatar(avatar);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setAvatar(null);
            userRepository.save(user);
            log.info("Avatar unlinked from user {}", user.getId());
        } else {
            avatarRepository.delete(avatar);
            log.info("Avatar deleted directly from DB, avatarId={}", id);
        }
        s3StorageService.deleteFile(avatar.getS3Key());
        log.info("Avatar successfully deleted for user from S3 {}", id);
    }
}
