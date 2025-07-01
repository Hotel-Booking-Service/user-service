package com.hbs.userservice.resolver;

import com.hbs.userservice.exception.domain.user.UserNotFoundException;
import com.hbs.userservice.model.User;
import com.hbs.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserResolver implements EntityResolver<User, UUID> {

    private final UserRepository userRepository;

    @Override
    public User resolveById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
