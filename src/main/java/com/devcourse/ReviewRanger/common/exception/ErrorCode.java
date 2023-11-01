package com.devcourse.ReviewRanger.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	EXIST_SAME_ID("이미 사용중인 아이디 입니다."),
	EXIST_SAME_EMAIL("이미 사용중인 이메일 입니다."),
	NOT_SAME_PASSWORD("동일한 비밀번호를 입력");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}
}
