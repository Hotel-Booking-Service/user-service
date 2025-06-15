package com.hbs.userservice.integration.repository;

import com.hbs.userservice.model.Avatar;
import com.hbs.userservice.model.Currency;
import com.hbs.userservice.model.Language;
import com.hbs.userservice.model.Notification;
import com.hbs.userservice.model.Preference;
import com.hbs.userservice.model.Role;
import com.hbs.userservice.model.User;
import com.hbs.userservice.repository.AvatarRepository;
import com.hbs.userservice.repository.PreferenceRepository;
import com.hbs.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;


    @Test
    void whenCreateUser_thenPersisted() {
        User saved = userRepository.save(createUser());

        assertNotNull(saved.getId());
        assertEquals("John", saved.getFirstName());
        assertNotNull(saved.getAvatar().getId());
        assertNotNull(saved.getPreference().getId());
    }

    @Test
    void whenFindById_thenReturnUser() {
        User user = userRepository.save(createUser());

        Optional<User> found = userRepository.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
    }

    @Test
    void whenUpdateUser_thenChangesPersisted() {
        User user = userRepository.save(createUser());

        user.setLastName("UpdatedLast");
        user.setPhoneNumber("+19999999999");

        User updated = userRepository.save(user);

        assertEquals("UpdatedLast", updated.getLastName());
        assertEquals("+19999999999", updated.getPhoneNumber());
    }

    @Test
    void whenDeleteUser_thenCascadeRemovesChildren() {
        User user = userRepository.save(createUser());
        Long avatarId = user.getAvatar().getId();
        Long prefId   = user.getPreference().getId();

        userRepository.delete(user);
        userRepository.flush();

        assertFalse(userRepository.findById(user.getId()).isPresent());
        assertFalse(avatarRepository.findById(avatarId).isPresent());
        assertFalse(preferenceRepository.findById(prefId).isPresent());
    }

    @Test
    void whenDuplicateEmail_thenThrowsException() {
        String email = UUID.randomUUID() + "@mail.com";

        User u1 = createUser();
        u1.setEmail(email);
        userRepository.saveAndFlush(u1);

        User u2 = createUser();
        u2.setEmail(email);

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.saveAndFlush(u2));
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(UUID.randomUUID() + "@example.com");
        user.setPhoneNumber("+1" + (1000000000 + LocalTime.now().getHour() * 8999999999L));
        user.setRole(Role.USER);

        Avatar avatar = new Avatar();
        avatar.setS3Key(UUID.randomUUID() + ".png");
        user.setAvatar(avatar);

        Preference pref = new Preference();
        pref.setLanguage(Language.RU);
        pref.setTimezone("UTC");
        pref.setCurrency(Currency.ZAR);
        pref.setNotification(Notification.EMAIL);
        user.setPreference(pref);

        user.setInterests(Set.of());

        return user;
    }
}
