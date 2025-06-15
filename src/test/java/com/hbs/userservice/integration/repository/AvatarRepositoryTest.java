package com.hbs.userservice.integration.repository;

import com.hbs.userservice.model.Avatar;
import com.hbs.userservice.repository.AvatarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class AvatarRepositoryTest {


    @Autowired
    private AvatarRepository avatarRepository;

    @Test
    void whenCreateAvatar_thenPersisted() {
        Avatar avatar = createAvatar();
        Avatar savedAvatar = avatarRepository.save(avatar);

        assertNotNull(savedAvatar.getId());
        assertEquals(avatar.getS3Key(), savedAvatar.getS3Key());
        assertNotNull(savedAvatar.getCreatedAt());
    }

    @Test
    void whenFindById_thenReturnAvatar() {
        Avatar avatar = avatarRepository.save(createAvatar());
        Optional<Avatar> found = avatarRepository.findById(avatar.getId());

        assertTrue(found.isPresent());
        assertEquals(avatar.getId(), found.get().getId());
    }

    @Test
    void whenUpdateAvatar_thenChangesPersisted() {
        Avatar avatar = avatarRepository.save(createAvatar());
        avatar.setS3Key(UUID.randomUUID() + ".png");
        Avatar updated = avatarRepository.save(avatar);

        assertEquals(avatar.getS3Key(), updated.getS3Key());
    }

    @Test
    void whenDeleteAvatar_thenRemoved() {
        Avatar avatar = avatarRepository.save(createAvatar());
        avatarRepository.delete(avatar);

        assertFalse(avatarRepository.findById(avatar.getId()).isPresent());
    }

    private Avatar createAvatar() {
        Avatar avatar = new Avatar();
        avatar.setS3Key(UUID.randomUUID() + ".png");
        return avatar;
    }
}
