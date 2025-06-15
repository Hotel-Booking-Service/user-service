package com.hbs.userservice.integration.repository;

import com.hbs.userservice.model.Currency;
import com.hbs.userservice.model.Language;
import com.hbs.userservice.model.Notification;
import com.hbs.userservice.model.Preference;
import com.hbs.userservice.repository.PreferenceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class PreferenceRepositoryTest {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Test
    void whenCreatePreference_thenPersisted() {
        Preference preference = createPreference();
        Preference saved = preferenceRepository.save(preference);

        assertNotNull(saved.getId());
        assertEquals(preference.getLanguage(), saved.getLanguage());
        assertEquals(preference.getTimezone(), saved.getTimezone());
        assertEquals(preference.getCurrency(), saved.getCurrency());
        assertEquals(preference.getNotification(), saved.getNotification());
    }

    @Test
    void whenFindById_thenReturnPreference() {
        Preference preference = preferenceRepository.save(createPreference());
        Optional<Preference> found = preferenceRepository.findById(preference.getId());

        assertTrue(found.isPresent());
        assertEquals(preference.getId(), found.get().getId());
    }

    @Test
    void whenUpdatePreference_thenChangesPersisted() {
        Preference preference = preferenceRepository.save(createPreference());
        preference.setTimezone("Europe/Moscow");
        preference.setLanguage(Language.ZH);
        preference.setCurrency(Currency.CNY);
        preference.setNotification(Notification.SMS);

        Preference updated = preferenceRepository.save(preference);

        assertEquals("Europe/Moscow", updated.getTimezone());
        assertEquals(Language.ZH, updated.getLanguage());
        assertEquals(Currency.CNY, updated.getCurrency());
        assertEquals(Notification.SMS, updated.getNotification());
    }

    @Test
    void whenDeletePreference_thenRemoved() {
        Preference preference = preferenceRepository.save(createPreference());
        preferenceRepository.delete(preference);

        assertFalse(preferenceRepository.findById(preference.getId()).isPresent());
    }

    private Preference createPreference() {
        Preference preference = new Preference();
        preference.setLanguage(Language.RU);
        preference.setTimezone("UTC");
        preference.setCurrency(Currency.RUB);
        preference.setNotification(Notification.SMS);
        return preference;
    }

}
