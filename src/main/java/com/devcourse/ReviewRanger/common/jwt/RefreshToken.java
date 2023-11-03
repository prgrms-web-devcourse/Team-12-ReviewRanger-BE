package com.devcourse.ReviewRanger.common.jwt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class RefreshToken extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "refresh_token", nullable = false, unique = true)
	private String refreshToken;

	public RefreshToken() {
	}

	public RefreshToken(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}
}
