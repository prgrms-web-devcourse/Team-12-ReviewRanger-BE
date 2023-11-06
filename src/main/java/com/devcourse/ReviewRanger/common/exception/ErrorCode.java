package com.devcourse.ReviewRanger.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// 401 error
	NOT_CORRECT_JWT(UNAUTHORIZED, "잘못된 JWT 토큰입니다."),
	EXPIRED_JWT_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
	LOGOUT_JWT_TOKEN(UNAUTHORIZED, "로그아웃된 토큰입니다."),
	NOT_SUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원하지 않는 토근입니다."),
	NOT_CORRECT_JWT_SIGN(UNAUTHORIZED, "잘못된 JWT SIGN값입니다."),
	NOT_AUTHORIZED_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

	// 404 error
	FAIL_USER_LOGIN(NOT_FOUND, "존재하지 않는 계정입니다."),

	// 409 error
	EXIST_SAME_NAME(CONFLICT, "이미 사용중인 이름 입니다."),
	EXIST_SAME_EMAIL(CONFLICT, "이미 사용중인 이메일 입니다."),

	//404 error
	NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자입니다."),
	NOT_FOUND_REVIEW(NOT_FOUND, "존재하지 않는 리뷰입니다."),
	NOT_FOUND_PARTICIPATION(NOT_FOUND, "해당하는 리뷰가 없습니다."),
	NOT_FOUND_REVIEW_TARGET(NOT_FOUND, "존재하지 않는 리뷰 대상입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	ErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
