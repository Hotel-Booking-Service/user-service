package com.hbs.userservice.repository;

import com.hbs.userservice.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}