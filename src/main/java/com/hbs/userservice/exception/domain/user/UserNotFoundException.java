package com.hbs.userservice.exception.domain.user;

import com.hbs.userservice.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "User not found";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
