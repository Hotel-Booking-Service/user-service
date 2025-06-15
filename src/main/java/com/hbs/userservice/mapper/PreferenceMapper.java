package com.hbs.userservice.mapper;

import com.hbs.userservice.dto.request.PatchPreferenceRequest;
import com.hbs.userservice.dto.response.PreferenceResponse;
import com.hbs.userservice.model.Preference;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PreferenceMapper {

    PreferenceResponse toResponse(Preference preference);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PatchPreferenceRequest patchPreferenceRequest, @MappingTarget Preference preference);
}
