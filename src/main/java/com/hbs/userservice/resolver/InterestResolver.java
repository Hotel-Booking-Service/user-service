package com.hbs.userservice.resolver;


import com.hbs.userservice.exception.domain.interest.InterestNotFoundException;
import com.hbs.userservice.model.Interest;
import com.hbs.userservice.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InterestResolver implements EntityResolver<Interest, Long> {

    private final InterestRepository interestRepository;

    @Override
    @Transactional(readOnly = true)
    public Interest resolveById(Long id) {
        return interestRepository.findById(id)
                .orElseThrow(InterestNotFoundException::new);
    }
}
