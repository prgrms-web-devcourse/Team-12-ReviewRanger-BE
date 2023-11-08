package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

public record ReceiverResponse(
	Long subjectId,

	String subjectName,

	Integer responserCount,

	List<Long> responserIds
) {
}
