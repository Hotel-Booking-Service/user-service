package com.hbs.userservice.mapper;

import com.hbs.userservice.dto.request.InterestRequest;
import com.hbs.userservice.dto.response.InterestResponse;
import com.hbs.userservice.model.Interest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InterestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Interest toEntity(InterestRequest interestRequest);

    InterestResponse toResponse(Interest interest);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(InterestRequest interestRequest, @MappingTarget Interest interest);
}
