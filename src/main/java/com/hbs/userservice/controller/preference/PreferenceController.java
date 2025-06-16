package com.hbs.userservice.controller.preference;

import com.hbs.userservice.dto.request.PatchPreferenceRequest;
import com.hbs.userservice.dto.response.PreferenceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Preference", description = "API для взаимодействия с настройками пользователя")
@RequestMapping("/api/v1/preferences")
public interface PreferenceController {

    @Operation(
            summary = "Получение настроек",
            description = "Получение всех настроек по id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Получение настроек"),
                    @ApiResponse(responseCode = "404", description = "Настройка не найдена")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PreferenceResponse getPreference(@PathVariable Long id);


    @Operation(
            summary = "Обновление настроек",
            description = "Patch обновление настроек"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Успешное обновление"),
                    @ApiResponse(responseCode = "404", description = "Настройка не найдена")
            }
    )
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PreferenceResponse patchPreference(@PathVariable Long id, @Validated @RequestBody PatchPreferenceRequest patchPreferenceRequest);
}
