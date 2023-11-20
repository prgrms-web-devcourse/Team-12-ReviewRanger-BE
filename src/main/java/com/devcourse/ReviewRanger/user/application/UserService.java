package com.devcourse.ReviewRanger.user.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;
import com.devcourse.ReviewRanger.user.dto.UserInfoResponse;
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
	public void updateName(Long id, String editName) {
		boolean isExistName = userRepository.existsByName(editName);
		if (isExistName) {
			throw new RangerException(EXIST_SAME_NAME);
		}

		User user = getUserOrThrow(id);
		user.updateName(editName);
	}

	@Transactional
	public void updatePassword(Long id, String editEncodedPassword) {
		User user = getUserOrThrow(id);
		editEncodedPassword = passwordEncoder.encode(editEncodedPassword);
		user.updatePassword(editEncodedPassword);
	}

	public User getUserOrThrow(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new RangerException(FAIL_USER_LOGIN));
	}

	public List<GetUserResponse> getAllUsers() {
		return userRepository.findAll().stream()
			.map(user -> new GetUserResponse(user.getId(), user.getName()))
			.toList();
	}

	public UserInfoResponse getUserInfo(UserPrincipal user) {
		Long id = user.getId();
		String name = user.getName();
		String email = user.getUsername();

		return new UserInfoResponse(id, name, email);
	}
}
