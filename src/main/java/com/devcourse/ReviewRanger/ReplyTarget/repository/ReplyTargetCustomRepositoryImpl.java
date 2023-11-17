package com.devcourse.ReviewRanger.ReplyTarget.repository;

import static com.devcourse.ReviewRanger.ReplyTarget.domain.QReplyTarget.*;

import java.util.List;

import org.springframework.util.StringUtils;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReplyTargetCustomRepositoryImpl implements ReplyTargetCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public ReplyTargetCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public List<ReplyTarget> findAllByParticipationIdToDynamic(Long participationId, String searchName) {
		BooleanBuilder builder = new BooleanBuilder();
		if (participationId != null) {
			builder.and(replyTarget.participationId.eq(participationId));
		}

		if (StringUtils.hasText(searchName)) {
			builder.and(replyTarget.receiver.name.contains(searchName));
		}

		List<ReplyTarget> replyTargets = jpaQueryFactory.selectFrom(replyTarget)
			.where(builder)
			.orderBy(replyTarget.receiver.name.asc())
			.fetch();

		return replyTargets;
	}
}
