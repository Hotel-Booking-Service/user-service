package com.hbs.userservice.dto.response;

import com.hbs.userservice.model.Currency;
import com.hbs.userservice.model.Language;
import com.hbs.userservice.model.Notification;

public record PreferenceResponse(
        Long id,
        Language language,
        String timezone,
        Currency currency,
        Notification notification) {
}
