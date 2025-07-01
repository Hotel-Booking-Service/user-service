package com.hbs.userservice.exception.domain.avatar;

import com.hbs.userservice.exception.base.InvalidException;

public class AvatarEmptyException extends InvalidException {
    private static final String ERROR_MESSAGE = "File is empty";

    public AvatarEmptyException() {
        super(ERROR_MESSAGE);
    }
}
