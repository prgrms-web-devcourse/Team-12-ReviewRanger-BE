package com.devcourse.ReviewRanger.ReplyTarget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;

public interface ReplyTargetRepository extends JpaRepository<ReplyTarget, Long> {
	List<ReplyTarget> findAllByParticipationId(Long participationId);

	List<ReplyTarget> findAllByReceiverId(Long receiverId);

	List<ReplyTarget> findAllByResponserId(Long responerId);
}
