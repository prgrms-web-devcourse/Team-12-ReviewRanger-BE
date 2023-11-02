package com.devcourse.ReviewRanger.surveyresult.dto.response;

public record Recipients(
	Long recipientId,

	String recipientName,

	Integer responserCount
) {
}
