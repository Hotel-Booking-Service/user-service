package com.hbs.userservice.controller.interest;

import com.hbs.userservice.dto.request.InterestRequest;
import com.hbs.userservice.dto.response.InterestResponse;
import com.hbs.userservice.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Interest", description = "API для взаимодействия с интересами пользователей")
@RequestMapping("/api/v1/interests")
public interface InterestController {

    @Operation(
            summary = "Получение всех интересов",
            description = "Получение всех интересов с pageable"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение интересов"),
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PageResponse<InterestResponse> getAllInterests(Pageable pageable);

    @Operation(
            summary = "Получение интереса по ID",
            description = "Получить интерес по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Интерес найден"),
            @ApiResponse(responseCode = "404", description = "Интерес не найден")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    InterestResponse getInterest(@PathVariable Long id);

    @Operation(
            summary = "Создание нового интереса",
            description = "Создание интереса по имени"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Интерес успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    InterestResponse createInterest(@Valid @RequestBody InterestRequest interestRequest);

    @Operation(
            summary = "Обновление интереса",
            description = "Полное обновление существующего интереса по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Интерес успешно обновлён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Интерес не найден")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    InterestResponse updateInterest(@PathVariable Long id, @Valid @RequestBody InterestRequest interestRequest);

    @Operation(
            summary = "Удаление интереса",
            description = "Удаляет интерес по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Интерес успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Интерес не найден")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInterest(@PathVariable Long id);
}
