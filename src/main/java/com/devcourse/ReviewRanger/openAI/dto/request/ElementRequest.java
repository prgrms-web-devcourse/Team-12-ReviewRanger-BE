package com.devcourse.ReviewRanger.openAI.dto.request;

import java.util.List;

public record ElementRequest(
	List<String> replies
) {
}
