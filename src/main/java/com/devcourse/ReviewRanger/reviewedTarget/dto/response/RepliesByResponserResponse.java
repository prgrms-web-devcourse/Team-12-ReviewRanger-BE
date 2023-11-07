package com.devcourse.ReviewRanger.reviewedTarget.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;

public record RepliesByResponserResponse(
	Long subjectId,

	String subjectName,

	List<ReplyResponse> replies
) {

}
