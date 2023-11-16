package com.devcourse.ReviewRanger.ReplyTarget.repository;

import java.util.List;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;

public interface ReplyTargetCustomRepository {
	List<ReplyTarget> findAllByParticipationIdToDynamic(Long participationId, String searchName, String sort);
}
