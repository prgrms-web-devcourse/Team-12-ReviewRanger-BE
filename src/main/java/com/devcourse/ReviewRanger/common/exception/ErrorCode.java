package com.devcourse.ReviewRanger.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// 409 error
	EXIST_SAME_NAME(CONFLICT, "이미 사용중인 이름 입니다."),
	EXIST_SAME_EMAIL(CONFLICT, "이미 사용중인 이메일 입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	ErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
