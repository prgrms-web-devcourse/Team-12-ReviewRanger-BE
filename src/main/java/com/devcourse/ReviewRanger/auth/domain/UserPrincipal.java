package com.devcourse.ReviewRanger.auth.domain;

import lombok.Getter;

@Getter
public class UserPrincipal {

	private final Long id;

	public UserPrincipal(Long id) {
		this.id = id;
	}
}
