package com.hbs.userservice.exception.domain.avatar;

import com.hbs.userservice.exception.base.NotFoundException;

public class AvatarNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Avatar not found";

    public AvatarNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
