package com.devcourse.ReviewRanger.common.exception;

import lombok.Getter;

@Getter
public class RangerException extends RuntimeException {

	public record ErrorResponse(
		String errorCode,
		String message
	) {
	}

	private final ErrorCode errorCode;

	public RangerException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
