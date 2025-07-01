package com.hbs.userservice.dto.response;

import java.time.Instant;

public record AvatarResponse(
        Long id,
        String s3Key,
        Instant createdAt
) {
}
