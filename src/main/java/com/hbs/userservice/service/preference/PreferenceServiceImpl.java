package com.hbs.userservice.service.preference;

import com.hbs.userservice.dto.request.PatchPreferenceRequest;
import com.hbs.userservice.dto.response.PreferenceResponse;
import com.hbs.userservice.mapper.PreferenceMapper;
import com.hbs.userservice.model.Preference;
import com.hbs.userservice.resolver.PreferenceResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceMapper preferenceMapper;
    private final PreferenceResolver preferenceResolver;


    @Override
    @Transactional(readOnly = true)
    public PreferenceResponse getPreference(Long id) {
        Preference preference = preferenceResolver.resolveById(id);
        return preferenceMapper.toResponse(preference);
    }

    @Override
    @Transactional
    public PreferenceResponse updatePreference(Long id, PatchPreferenceRequest patchPreferenceRequest) {
        Preference preference = preferenceResolver.resolveById(id);
        preferenceMapper.updateEntity(patchPreferenceRequest, preference);
        return preferenceMapper.toResponse(preference);
    }
}
