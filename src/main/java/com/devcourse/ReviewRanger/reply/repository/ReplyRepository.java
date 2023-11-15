package com.devcourse.ReviewRanger.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.reply.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
