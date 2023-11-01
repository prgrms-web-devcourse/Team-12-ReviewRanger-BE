package com.devcourse.ReviewRanger.response.dto.request;

import java.util.List;

public record Results(
	Long reviewerId,

	List<Answers> answers
) {
}
