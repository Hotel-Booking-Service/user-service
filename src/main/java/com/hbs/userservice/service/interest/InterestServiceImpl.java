package com.hbs.userservice.service.interest;

import com.hbs.userservice.dto.request.InterestRequest;
import com.hbs.userservice.dto.response.InterestResponse;
import com.hbs.userservice.dto.response.PageResponse;
import com.hbs.userservice.mapper.InterestMapper;
import com.hbs.userservice.model.Interest;
import com.hbs.userservice.repository.InterestRepository;
import com.hbs.userservice.resolver.InterestResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;
    private final InterestMapper interestMapper;
    private final InterestResolver interestResolver;


    @Override
    @Transactional(readOnly = true)
    public PageResponse<InterestResponse> getInterests(Pageable pageable) {
        Page<Interest> page = interestRepository.findAll(pageable);
        List<InterestResponse> content = page.getContent().stream()
                .map(interestMapper::toResponse)
                .toList();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public InterestResponse getInterest(Long id) {
        Interest interest = interestResolver.resolveById(id);
        return interestMapper.toResponse(interest);
    }

    @Override
    @Transactional
    public InterestResponse createInterest(InterestRequest interestRequest) {
        Interest interest = interestMapper.toEntity(interestRequest);
        interest = interestRepository.save(interest);
        return interestMapper.toResponse(interest);
    }

    @Override
    @Transactional
    public InterestResponse updateInterest(Long id, InterestRequest interestRequest) {
        Interest interest = interestResolver.resolveById(id);
        interestMapper.updateEntity(interestRequest, interest);
        return interestMapper.toResponse(interest);
    }

    @Override
    @Transactional
    public void deleteInterest(Long id) {
        Interest interest = interestResolver.resolveById(id);
        interestRepository.delete(interest);
    }
}
