package com.devcourse.ReviewRanger.reviewedTarget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

public interface ReviewedTargetRepository extends JpaRepository<ReviewedTarget, Long> {
	List<ReviewedTarget> findAllByParticipationId(Long participationId);

	List<ReviewedTarget> findAllByReceiverId(Long receiverId);

	List<ReviewedTarget> findAllByResponserId(Long responerId);
}
