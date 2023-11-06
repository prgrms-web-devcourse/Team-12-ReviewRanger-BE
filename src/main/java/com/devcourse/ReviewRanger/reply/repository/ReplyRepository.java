package com.devcourse.ReviewRanger.reply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.reply.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByReviewedTargetId(Long reviewedTargetId);
}
