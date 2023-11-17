package com.devcourse.ReviewRanger.participation.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.devcourse.ReviewRanger.participation.domain.Participation;

public interface ParticipationCustomRepository {

	List<Participation> findAllByReviewIdToDynamic(Long reviewId, String searchName, String sort);

	Slice<Participation> findByResponserId(Long cursorId, Long responserId, Pageable pageable);
}
