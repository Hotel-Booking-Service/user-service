package com.hbs.userservice.resolver;

import com.hbs.userservice.exception.domain.avatar.AvatarNotFoundException;
import com.hbs.userservice.model.Avatar;
import com.hbs.userservice.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvatarResolver implements EntityResolver<Avatar, Long> {

    private final AvatarRepository avatarRepository;

    @Override
    public Avatar resolveById(Long id) {
        return avatarRepository.findById(id)
                .orElseThrow(AvatarNotFoundException::new);
    }
}
