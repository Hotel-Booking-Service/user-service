package com.hbs.userservice.integration.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hbs.userservice.dto.request.PatchPreferenceRequest;
import com.hbs.userservice.dto.response.ErrorResponse;
import com.hbs.userservice.dto.response.PreferenceResponse;
import com.hbs.userservice.model.Currency;
import com.hbs.userservice.model.Language;
import com.hbs.userservice.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PreferenceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DataSet(value = {"dataset/preference/preferences.yaml"}, cleanBefore = true, cleanAfter = true)
    void getPreference_shouldReturnPreference() {

        PreferenceResponse preference = restTemplate.getForObject("/api/v1/preferences/1", PreferenceResponse.class);
        assertNotNull(preference);
        assertEquals(Language.RU, preference.language());
        assertEquals(Currency.RUB, preference.currency());
    }

    @Test
    void getPreference_whenNotFound_shouldReturn404() {
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                "/api/v1/preferences/14488",
                ErrorResponse.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DataSet(value = {"dataset/preference/preferences.yaml"}, cleanBefore = true, cleanAfter = true)
    void patchPreference_shouldPatchPreference() {
        PatchPreferenceRequest patchPreferenceRequest = new PatchPreferenceRequest();
        patchPreferenceRequest.setLanguage(Language.TR);

        PreferenceResponse preference = restTemplate.patchForObject("/api/v1/preferences/1", patchPreferenceRequest, PreferenceResponse.class);
        assertNotNull(preference);
        assertEquals(Language.TR, preference.language());
        assertEquals(Currency.RUB, preference.currency());
        assertEquals(Notification.SMS, preference.notification());
    }

}
