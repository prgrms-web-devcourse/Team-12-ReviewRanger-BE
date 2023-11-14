package com.devcourse.ReviewRanger.openAI.dto.response;

import com.devcourse.ReviewRanger.openAI.dto.common.Message;

public record Choice(
	int index,

	Message message
) {
}
