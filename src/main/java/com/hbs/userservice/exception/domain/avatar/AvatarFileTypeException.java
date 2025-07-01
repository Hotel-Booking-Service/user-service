package com.hbs.userservice.exception.domain.avatar;

import com.hbs.userservice.exception.base.InvalidException;

public class AvatarFileTypeException extends InvalidException {
    private static final String ERROR_MESSAGE = "Non - correct file format";

    public AvatarFileTypeException() {
        super(ERROR_MESSAGE);
    }
}
