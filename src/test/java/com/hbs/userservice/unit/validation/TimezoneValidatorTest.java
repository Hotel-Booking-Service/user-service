package com.hbs.userservice.unit.validation;

import com.hbs.userservice.validation.TimezoneValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimezoneValidatorTest {

    private final TimezoneValidator validator = new TimezoneValidator();

    @Test
    void shouldReturnTrueForValidTimezone() {
        String timezone = "Europe/Moscow";
        boolean result = validator.isValid(timezone, null);
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseForInvalidTimezone() {
        String timezone = "Hohlandia/Dima";
        boolean result = validator.isValid(timezone, null);
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenValueIsNull() {
        boolean result = validator.isValid(null, null);
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenValueIsEmpty() {
        boolean result = validator.isValid("", null);
        assertThat(result).isFalse();
    }

}
