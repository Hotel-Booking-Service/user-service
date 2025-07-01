package com.hbs.userservice.mapper;

import com.hbs.userservice.dto.response.AvatarResponse;
import com.hbs.userservice.model.Avatar;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AvatarMapper {
    AvatarResponse toAvatarResponse(Avatar avatar);
}
