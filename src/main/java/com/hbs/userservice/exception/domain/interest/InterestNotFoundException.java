package com.hbs.userservice.exception.domain.interest;

import com.hbs.userservice.exception.base.NotFoundException;

public class InterestNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Interest not found";

    public InterestNotFoundException() {
        super(ERROR_MESSAGE );
    }
}
