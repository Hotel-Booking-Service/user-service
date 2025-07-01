package com.hbs.userservice.controller.avatar;

import com.hbs.userservice.dto.response.AvatarResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Avatar", description = "API для взаимодействия с аватарами пользователей")
@RequestMapping("/api/v1/avatars")
public interface AvatarController {

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AvatarResponse uploadAvatar(@RequestPart("file") MultipartFile file);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    URI getAvatar(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAvatar(@PathVariable("id") Long id);
}
