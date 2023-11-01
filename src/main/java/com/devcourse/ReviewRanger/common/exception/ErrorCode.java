package com.devcourse.ReviewRanger.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	EXIST_SAME_NAME("이미 사용중인 이름 입니다."),
	EXIST_SAME_EMAIL("이미 사용중인 이메일 입니다.");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}
}
