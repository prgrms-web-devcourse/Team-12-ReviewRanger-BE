package com.devcourse.ReviewRanger.common.exception;

import lombok.Getter;

@Getter
public class RangerException extends RuntimeException {

	private final ErrorCode errorCode;

	public RangerException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
