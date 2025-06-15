package com.hbs.userservice.service.preference;

import com.hbs.userservice.dto.request.PatchPreferenceRequest;
import com.hbs.userservice.dto.response.PreferenceResponse;

public interface PreferenceService {

    PreferenceResponse getPreference(Long id);

    PreferenceResponse updatePreference(Long id, PatchPreferenceRequest patchPreferenceRequest);
}
