package com.devcourse.ReviewRanger.common.exception;

import static com.devcourse.ReviewRanger.common.exception.RangerException.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RangerException.class)
	@ResponseBody
	public ErrorResponse handleRangerException(RangerException e) {
		ErrorCode errorCode = e.getErrorCode();
		return new ErrorResponse(errorCode.name(), errorCode.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ErrorResponse handleRangerException(MethodArgumentNotValidException e) {
		String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return new ErrorResponse(BAD_REQUEST.name(), errorMessage);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ErrorResponse handleRuntimeException(RuntimeException e) {
		return new ErrorResponse(INTERNAL_SERVER_ERROR.name(), e.getMessage());
	}

}
