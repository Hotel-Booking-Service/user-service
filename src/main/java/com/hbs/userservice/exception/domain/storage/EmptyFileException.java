package com.hbs.userservice.exception.domain.storage;

import com.hbs.userservice.exception.base.InvalidException;

public class EmptyFileException extends InvalidException {
    private static final String ERROR_MESSAGE = "The file is empty";

    public EmptyFileException() {
        super(ERROR_MESSAGE);
    }
}
