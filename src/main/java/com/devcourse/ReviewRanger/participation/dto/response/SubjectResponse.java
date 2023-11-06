package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

public record SubjectResponse(
	Long subjectId,

	String subjectName,

	Integer responserCount,

	List<Long> responserIds
) {
}
