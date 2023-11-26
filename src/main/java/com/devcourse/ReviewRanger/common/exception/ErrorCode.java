package com.devcourse.ReviewRanger.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	//400 error
	MISSING_REQUIRED_QUESTION_REPLY(BAD_REQUEST, "필수 질문의 답변이 존재하지 않습니다."),
	MISSING_IMAGE_CONVERT(BAD_REQUEST, "MultipartFile -> File 전환에 실패했습니다."),
	EMPTY_IMAGE(BAD_REQUEST, "이미지가 없습니다."),
	INVALID_IMAGE_EXTENSION(BAD_REQUEST, "이미지 확장자가 올바르지 않습니다."),

	// 401 error
	NOT_CORRECT_JWT(UNAUTHORIZED, "잘못된 JWT 토큰입니다."),
	EXPIRED_JWT_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
	LOGOUT_JWT_TOKEN(UNAUTHORIZED, "로그아웃된 토큰입니다."),
	NOT_SUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원하지 않는 토근입니다."),
	NOT_CORRECT_JWT_SIGN(UNAUTHORIZED, "잘못된 JWT SIGN값입니다."),
	NOT_AUTHORIZED_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

	// 404 error
	FAIL_USER_LOGIN(NOT_FOUND, "존재하지 않는 계정입니다."),
	NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자입니다."),
	NOT_FOUND_REVIEW(NOT_FOUND, "존재하지 않는 리뷰입니다."),

	NOT_FOUND_QUESTION(NOT_FOUND, "존재하지 않는 질문입니다."),
	NOT_FOUND_PARTICIPATION(NOT_FOUND, "해당하는 리뷰가 없습니다."),
	NOT_FOUND_REVIEW_TARGET(NOT_FOUND, "존재하지 않는 리뷰 대상입니다."),
	NOT_FOUND_REPLY(NOT_FOUND, "존재하지 않는 답변입니다."),
	NOT_FOUND_QUESTION_TYPE(NOT_FOUND, "존재하지 않는 질문 타입입니다."),

	NOT_FOUND_QUESTION_OPTION(NOT_FOUND, "존재하지 않는 질문 옵션입니다."),
	NOT_FOUND_FINAL_REVIEW_RESULT(NOT_FOUND, "존재하지 않는 최종 리뷰 결과입니다."),
	NOT_FOUND_FINAL_REVIEW_ANSWER_OF_SUBJECT(NOT_FOUND, "존재하지 않는 주관식 답변입니다."),
	NOT_FOUND_PARTICIPANTS(NOT_FOUND, "참여 목록이 존재하지 않습니다."),

	// 409 error
	EXIST_SAME_NAME(CONFLICT, "이미 사용중인 이름 입니다."),
	EXIST_SAME_EMAIL(CONFLICT, "이미 사용중인 이메일 입니다."),
	NOT_OWNER_OF_FINAL_REVIEW_RESULT(CONFLICT, "리뷰 결과의 주인이 아닙니다."),
	NOT_FINISHED_PARTICIPANTS(CONFLICT, "리뷰가 모두 제출되지 않았습니다."),
	NOT_REMOVE_AFTER_DEADLINE_REVIEW(CONFLICT, "마감, 종료된 리뷰는 삭제할 수 없습니다."),

	//500 error
	NOT_RECEIVED_RESPONSE_FROM_OPEN_AI_API(INTERNAL_SERVER_ERROR, "OPEN AI API로부터 응답받지 못했습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	ErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
