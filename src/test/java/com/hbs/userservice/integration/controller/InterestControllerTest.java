package com.hbs.userservice.integration.controller;


import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hbs.userservice.dto.request.InterestRequest;
import com.hbs.userservice.dto.response.ErrorResponse;
import com.hbs.userservice.dto.response.InterestResponse;
import com.hbs.userservice.dto.response.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InterestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DataSet(value = {"dataset/interest/interests.yaml"}, cleanBefore = true, cleanAfter = true)
    void getAllInterests_shouldReturnPagedResponse() {
        ResponseEntity<PageResponse<InterestResponse>> response = restTemplate.exchange(
                "/api/v1/interests",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageResponse<InterestResponse>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().content().isEmpty());
    }

    @Test
    @DataSet(value = {"dataset/interest/interests.yaml"}, cleanBefore = true, cleanAfter = true)
    void getInterest_shouldReturnInterest() {
        InterestResponse response = restTemplate.getForObject("/api/v1/interests/1", InterestResponse.class);
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Кино", response.name());
    }

    @Test
    void getInterest_whenNotFound_shouldReturn404() {
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                "/api/v1/interests/9999",
                ErrorResponse.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void createInterest_shouldReturnCreatedInterest() {
        InterestRequest request = new InterestRequest("Футбол");

        ResponseEntity<InterestResponse> response = restTemplate.postForEntity(
                "/api/v1/interests",
                request,
                InterestResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Футбол", response.getBody().name());
    }

    @Test
    @DataSet(value = {"dataset/interest/interests.yaml"}, cleanBefore = true, cleanAfter = true)
    void updateInterest_shouldUpdateInterest() {
        InterestRequest request = new InterestRequest("Музыка");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InterestRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<InterestResponse> response = restTemplate.exchange(
                "/api/v1/interests/1",
                HttpMethod.PUT,
                entity,
                InterestResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Музыка", response.getBody().name());
    }

    @Test
    @DataSet(value = {"dataset/interest/interests.yaml"}, cleanBefore = true, cleanAfter = true)
    void deleteInterest_shouldDeleteSuccessfully() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/interests/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteInterest_whenNotFound_shouldReturn404() {
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/v1/interests/9999",
                HttpMethod.DELETE,
                null,
                ErrorResponse.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
