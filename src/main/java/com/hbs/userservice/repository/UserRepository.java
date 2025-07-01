package com.hbs.userservice.repository;

import com.hbs.userservice.model.Avatar;
import com.hbs.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAvatar(Avatar avatar);
}