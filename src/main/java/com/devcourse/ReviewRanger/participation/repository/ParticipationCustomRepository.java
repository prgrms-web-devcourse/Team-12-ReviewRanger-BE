package com.devcourse.ReviewRanger.participation.repository;

import org.springframework.data.domain.Slice;

import com.devcourse.ReviewRanger.participation.domain.Participation;

public interface ParticipationCustomRepository {
	Slice<Participation> findByResponserId(Long cursorId, Long userId, Integer size);
}
