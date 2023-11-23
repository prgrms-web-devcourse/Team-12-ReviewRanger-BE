package com.devcourse.ReviewRanger.ReplyTarget.repository;

import java.util.List;

import com.devcourse.ReviewRanger.participation.dto.response.ReceiverResponse;

public interface ReplyTargetCustomRepository {
	List<ReceiverResponse> findAllByParticipationIdToDynamic(Long participationId, String searchName);
}
