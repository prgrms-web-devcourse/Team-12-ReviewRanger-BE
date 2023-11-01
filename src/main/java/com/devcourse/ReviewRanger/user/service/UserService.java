package com.devcourse.ReviewRanger.user.service;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Boolean join(JoinRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPassword);

		if (checkName(request.name())) {
			throw new RangerException(EXIST_SAME_ID);
		}

		if (checkEmail(request.email())) {
			throw new RangerException(EXIST_SAME_EMAIL);
		}

		return (userRepository.save(newUser).getId()) > 0;
	}

	public boolean checkName(String name) {
		return userRepository.existsByName(name);
	}

	public boolean checkEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}
