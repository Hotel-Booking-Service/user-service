package com.hbs.userservice.dto.request;

import com.hbs.userservice.model.Currency;
import com.hbs.userservice.model.Language;
import com.hbs.userservice.model.Notification;
import com.hbs.userservice.validation.annotation.ValidTimezone;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PatchPreferenceRequest {

    private Language language;

    @ValidTimezone
    private String timezone;

    private Currency currency;

    private Notification notification;
}
