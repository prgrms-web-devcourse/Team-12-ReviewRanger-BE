package com.devcourse.ReviewRanger.common.exception;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String VALID_LOG_FORMAT = "Class : {}, Error : {}, ErrorMessage : {}";
	private static final String LOG_FORMAT = "Class : {}, ErrorMessage : {}";

	public record ErrorResponse(
		String message
	) {
	}

	@ExceptionHandler(RangerException.class)
	public ResponseEntity<ErrorResponse> handleRangerException(RangerException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.error(LOG_FORMAT, e.getClass().getSimpleName(), errorCode);

		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		log.error(VALID_LOG_FORMAT, e.getClass().getSimpleName(), "@Valid", errorMessage);

		return ResponseEntity.status(BAD_REQUEST)
			.body(new ErrorResponse(errorMessage));
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
		String errorMessage = e.getMessage();
		log.error(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

		return ResponseEntity.status(BAD_REQUEST)
			.body(new ErrorResponse(errorMessage));
	}

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ErrorResponse> handleJsonProcessingException(JsonProcessingException e) {
		String errorMessage = e.getMessage();
		log.error(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

		return ResponseEntity.status(BAD_REQUEST)
			.body(new ErrorResponse(errorMessage));
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		String errorMessage = e.getMessage();
		log.error(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

		return ResponseEntity.status(PAYLOAD_TOO_LARGE)
			.body(new ErrorResponse(FILE_MAX_SIZE.getMessage()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		log.error(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());

		return ResponseEntity.status(INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse(e.getMessage()));
	}
}
