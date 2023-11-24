package com.devcourse.ReviewRanger.ReplyTarget.repository;

import static com.devcourse.ReviewRanger.ReplyTarget.domain.QReplyTarget.*;

import java.util.List;

import org.springframework.util.StringUtils;

import com.devcourse.ReviewRanger.participation.dto.response.ReceiverResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReplyTargetCustomRepositoryImpl implements ReplyTargetCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public ReplyTargetCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public List<ReceiverResponse> findAllByParticipationIdToDynamic(Long reviewId, String searchName) {
		BooleanBuilder builder = new BooleanBuilder();
		if (reviewId != null) {
			builder.and(replyTarget.reviewId.eq(reviewId));
		}

		if (StringUtils.hasText(searchName)) {
			builder.and(replyTarget.receiver.name.contains(searchName));
		}

		List<ReceiverResponse> replyTargets = jpaQueryFactory.select(
				Projections.constructor(
					ReceiverResponse.class,
					replyTarget.receiver.id.as("receiverId"),
					replyTarget.receiver.name.as("receiverName"),
					replyTarget.id.count().as("responserCount")
				)
			)
			.from(replyTarget)
			.where(builder)
			.groupBy(replyTarget.receiver.id)
			.orderBy(replyTarget.receiver.name.asc())
			.fetch();

		return replyTargets;
	}
}
