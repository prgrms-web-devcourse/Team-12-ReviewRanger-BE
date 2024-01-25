package com.devcourse.ReviewRanger.user;

import com.devcourse.ReviewRanger.auth.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;

public enum UserFixture {

	SUYEON_FIXTURE("장수연", "dev12341@devcource.com", "abc12341234", "이미지경로1"),
	BEOMCHUL_FIXTURE("신범철", "dev12342@devcource.com", "abc12341234", "이미지경로1"),
	JUWOONG_FIXTURE("김주웅", "dev12343@devcource.com", "abc12341234", "이미지경로1"),
	SPENCER_FIXTURE("스펜서", "dev12344@devcource.com", "abc12341234", "이미지경로1");

	private final String name;
	private final String email;
	private final String password;
	private final String path;

	UserFixture(String name, String email, String password, String path) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.path = path;
	}

	public User toEntity() {
		return new User(name, email, password);
	}

	public JoinRequest toJoinRequest() {
		return new JoinRequest(name, email, password);
	}

	public GetUserResponse toGetUserResponse() {
		Long tempUserId = 1L;
		return new GetUserResponse(tempUserId, name, path);
	}
}
