package com.hbs.userservice.exception.domain.storage;

import com.hbs.userservice.exception.base.NotFoundException;

public class FileNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "File not found";

    public FileNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
