package com.hbs.userservice.repository;

import com.hbs.userservice.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}