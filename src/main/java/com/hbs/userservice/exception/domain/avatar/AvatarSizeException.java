package com.hbs.userservice.exception.domain.avatar;

import com.hbs.userservice.exception.base.InvalidException;

public class AvatarSizeException extends InvalidException {
    private static final String ERROR_MESSAGE = "Not correct avatar weight";

    public AvatarSizeException() {
        super(ERROR_MESSAGE);
    }
}
