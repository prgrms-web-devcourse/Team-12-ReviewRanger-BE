package com.devcourse.ReviewRanger.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByEmail(String email);

	Boolean existsByName(String name);

	Optional<User> findByEmail(String email);

	Optional<User> findByName(String name);
}
