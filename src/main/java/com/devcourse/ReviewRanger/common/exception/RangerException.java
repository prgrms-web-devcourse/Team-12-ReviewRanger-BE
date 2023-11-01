package com.devcourse.ReviewRanger.common.exception;

public class RangerException extends RuntimeException {

	private final ErrorCode errorCode;

	public RangerException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
