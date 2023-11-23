package com.devcourse.ReviewRanger.ReplyTarget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;

public interface ReplyTargetRepository extends JpaRepository<ReplyTarget, Long>, ReplyTargetCustomRepository {
	List<ReplyTarget> findAllByParticipationId(Long participationId);

	List<ReplyTarget> findAllByReviewIdAndReceiverId(Long reviewId, Long receiverId);

	List<ReplyTarget> findAllByReviewIdAndResponserId(Long reviewId, Long responerId);
}
