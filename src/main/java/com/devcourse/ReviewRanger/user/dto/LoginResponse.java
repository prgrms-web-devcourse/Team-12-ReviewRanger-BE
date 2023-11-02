package com.devcourse.ReviewRanger.user.dto;

public record LoginResponse(
	String accessToken,
	String tokenType
) {
}
