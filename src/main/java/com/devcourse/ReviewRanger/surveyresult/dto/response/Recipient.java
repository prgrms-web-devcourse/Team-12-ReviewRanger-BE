package com.devcourse.ReviewRanger.surveyresult.dto.response;

import java.util.List;

public record Recipient(
	Long recipientId,

	String recipientName,

	Integer responserCount,

	List<Long> responserIds
) {
}
