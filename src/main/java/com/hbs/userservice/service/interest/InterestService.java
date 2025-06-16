package com.hbs.userservice.service.interest;

import com.hbs.userservice.dto.request.InterestRequest;
import com.hbs.userservice.dto.response.InterestResponse;
import com.hbs.userservice.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface InterestService {

    PageResponse<InterestResponse> getInterests(Pageable pageable);

    InterestResponse getInterest(Long id);

    InterestResponse createInterest(InterestRequest interestRequest);

    InterestResponse updateInterest(Long id, InterestRequest interestRequest);

    void deleteInterest(Long id);
}
