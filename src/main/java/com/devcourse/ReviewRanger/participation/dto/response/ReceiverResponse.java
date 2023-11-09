package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

public record ReceiverResponse(
	Long receiverId,

	String receiverName,

	Integer responserCount,

	List<Long> responserIds
) {
}
