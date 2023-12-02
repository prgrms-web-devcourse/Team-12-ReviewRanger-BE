package com.devcourse.ReviewRanger.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.devcourse.ReviewRanger.common.entity.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class RefreshToken extends BaseEntity {

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "refresh_token", nullable = false, unique = true)
	private String refreshToken;

	protected RefreshToken() {
	}

	public RefreshToken(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

	public void update(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
