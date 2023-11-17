package com.devcourse.ReviewRanger.finalReviewResult.repository;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.QFinalReviewResult.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.devcourse.ReviewRanger.finalReviewResult.domain.QFinalReviewResult;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class FinalReviewResultCustomRepositoryImpl implements FinalReviewResultCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public FinalReviewResultCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Slice<FinalReviewResultListResponse> findAllFinalReviewResults(Long cursorId, Long userId,
		Pageable pageable) {
		QFinalReviewResult finalReviewResult = QFinalReviewResult.finalReviewResult;
		int pageSize = pageable.getPageSize();

		List<FinalReviewResultListResponse> results = jpaQueryFactory
			.select(
				Projections.constructor(
					FinalReviewResultListResponse.class,
					finalReviewResult.id,
					finalReviewResult.title,
					finalReviewResult.createAt.as("createdAt"))
			)
			.from(finalReviewResult)
			.where(
				finalReviewResult.userId.eq(userId)
					.and(eqCursorId(cursorId))
					.and(finalReviewResult.status.eq(SENT))
			)
			.orderBy(finalReviewResult.id.asc())
			.limit(pageSize + 1)
			.fetch();

		boolean hasNext = false;
		if (results.size() > pageSize) {
			results.remove(pageSize);
			hasNext = true;
		}

		return new SliceImpl<>(results, pageable, hasNext);
	}

	private BooleanExpression eqCursorId(Long cursorId) {
		return (cursorId == null) ? null : finalReviewResult.id.gt(cursorId);
	}
}
