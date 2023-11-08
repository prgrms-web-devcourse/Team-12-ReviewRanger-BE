package com.devcourse.ReviewRanger.reviewedTarget.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RepliesByResponserResponse(
	Long subjectId,

	String subjectName,

	@JsonProperty("replies")
	List<ReplyResponse> replies
) {

}
