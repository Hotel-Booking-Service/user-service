package com.hbs.userservice.controller.interest;

import com.hbs.userservice.dto.request.InterestRequest;
import com.hbs.userservice.dto.response.InterestResponse;
import com.hbs.userservice.dto.response.PageResponse;
import com.hbs.userservice.service.interest.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interests")
public class InterestControllerImpl implements InterestController {

    private final InterestService interestService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<InterestResponse> getAllInterests(Pageable pageable) {
        return interestService.getInterests(pageable);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InterestResponse getInterest(@PathVariable Long id) {
        return interestService.getInterest(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterestResponse createInterest(@RequestBody @Validated InterestRequest interestRequest) {
        return interestService.createInterest(interestRequest);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InterestResponse updateInterest(@PathVariable Long id, @RequestBody @Validated InterestRequest interestRequest) {
        return interestService.updateInterest(id, interestRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterest(@PathVariable Long id) {
        interestService.deleteInterest(id);
    }
}
