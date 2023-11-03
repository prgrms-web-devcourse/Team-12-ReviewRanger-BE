package com.devcourse.ReviewRanger.common.jwt;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByUserId(Long userId);

	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}