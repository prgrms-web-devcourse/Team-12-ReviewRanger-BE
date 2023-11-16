package com.devcourse.ReviewRanger.participation.repository;

import java.util.List;

import com.devcourse.ReviewRanger.participation.domain.Participation;

public interface ParticipationCustomRepository {
	List<Participation> findAllByReviewId(Long reviewId, String searchName, String sort);
}
