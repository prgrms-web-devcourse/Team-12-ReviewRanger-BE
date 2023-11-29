package com.devcourse.ReviewRanger.auth.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.auth.domain.RefreshToken;
import com.devcourse.ReviewRanger.auth.dto.JoinRequest;
import com.devcourse.ReviewRanger.auth.dto.LoginRequest;
import com.devcourse.ReviewRanger.auth.dto.LoginResponse;
import com.devcourse.ReviewRanger.auth.repository.RefreshTokenRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.common.jwt.JwtTokenProvider;
import com.devcourse.ReviewRanger.common.redis.RedisUtil;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisUtil redisUtil;
	private final RefreshTokenRepository refreshTokenRepository;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
		AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider,
		RedisUtil redisUtil, RefreshTokenRepository refreshTokenRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.redisUtil = redisUtil;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Transactional
	public Boolean join(JoinRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPassword);

		if (!isNotExistEmail(request.email())) {
			throw new RangerException(EXIST_SAME_EMAIL);
		}

		if (!isNotExistName(request.name())) {
			throw new RangerException(EXIST_SAME_NAME);
		}

		return (userRepository.save(newUser).getId()) > 0;
	}

	@Transactional
	public LoginResponse login(LoginRequest request) {
		// user 검증
		String userEmail = request.email();
		UsernamePasswordAuthenticationToken authenticationToken
			= new UsernamePasswordAuthenticationToken(userEmail, request.password());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// token 생성
		String accessToken = jwtTokenProvider.createAccessToken(authentication);
		String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

		// refreshToken 저장
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RangerException(NOT_FOUND_USER));
		refreshTokenRepository.findByUserId(user.getId())
			.ifPresentOrElse(
				token -> token.update(refreshToken),
				() -> {
					RefreshToken savedRefreshToken = new RefreshToken(user.getId(), refreshToken);
					refreshTokenRepository.save(savedRefreshToken);
				}
			);

		return new LoginResponse(accessToken, "Bearer");
	}

	public void logout(String accessToken) {
		accessToken = getAccessToken(accessToken);
		redisUtil.setBlackList(accessToken, "accessToken", 60 * 24);
	}

	private String getAccessToken(String accessToken) {
		String[] tokenSplit = accessToken.split("\\s");
		return tokenSplit[1];
	}

	public boolean isNotExistName(String name) {
		return !userRepository.existsByName(name);
	}

	public boolean isNotExistEmail(String email) {
		return !userRepository.existsByEmail(email);
	}
}
