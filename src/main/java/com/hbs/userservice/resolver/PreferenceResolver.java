package com.hbs.userservice.resolver;

import com.hbs.userservice.exception.domain.preference.PreferenceNotFoundException;
import com.hbs.userservice.model.Preference;
import com.hbs.userservice.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceResolver implements EntityResolver<Preference> {

    private final PreferenceRepository preferenceRepository;

    @Override
    @Transactional(readOnly = true)
    public Preference resolveById(Long id) {
        return preferenceRepository.findById(id)
                .orElseThrow(PreferenceNotFoundException::new);
    }
}
