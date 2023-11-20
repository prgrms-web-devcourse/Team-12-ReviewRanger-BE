package com.devcourse.ReviewRanger.user.service;

import static com.devcourse.ReviewRanger.user.service.UserFixture.*;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;

@Profile("test")
public class TestPrincipalDetailsService implements UserDetailsService {

	public static final String USER_EMAIL = "dev1234@devcource.com";

	private User getUser() {
		return SUYEON_FIXTURE.toEntity();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email.equals(USER_EMAIL)) {
			return new UserPrincipal(getUser());
		}
		return null;
	}
}