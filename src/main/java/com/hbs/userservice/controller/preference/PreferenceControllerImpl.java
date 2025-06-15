package com.hbs.userservice.controller.preference;

import com.hbs.userservice.dto.request.PatchPreferenceRequest;
import com.hbs.userservice.dto.response.PreferenceResponse;
import com.hbs.userservice.service.preference.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceControllerImpl implements PreferenceController {

    private final PreferenceService preferenceService;

    @Override
    public PreferenceResponse getPreference(Long id) {
        return preferenceService.getPreference(id);
    }

    @Override
    public PreferenceResponse patchPreference(Long id, PatchPreferenceRequest patchPreferenceRequest) {
        return preferenceService.updatePreference(id, patchPreferenceRequest);
    }

}
