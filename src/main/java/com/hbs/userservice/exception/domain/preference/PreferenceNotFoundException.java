package com.hbs.userservice.exception.domain.preference;

import com.hbs.userservice.exception.base.NotFoundException;

public class PreferenceNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Preference not found";

    public PreferenceNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
