package com.devcourse.ReviewRanger.user.service;

import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;

public enum UserFixture {

	SUYEON_FIXTURE("장수연", "dev1234@devcource.com", "abc12341234");

	private final String name;
	private final String email;
	private final String password;

	UserFixture(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User toEntity() {
		return new User(name, email, password);
	}

	public JoinRequest toJoinRequest() {
		return new JoinRequest(name, email, password);
	}
}
