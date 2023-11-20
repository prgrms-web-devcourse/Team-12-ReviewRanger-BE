package com.devcourse.ReviewRanger.auth.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(UserPrincipal::new)
			.orElseThrow(() -> new RangerException(FAIL_USER_LOGIN));
	}
}
