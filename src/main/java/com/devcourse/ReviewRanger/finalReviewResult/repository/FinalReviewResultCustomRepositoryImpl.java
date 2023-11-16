package com.devcourse.ReviewRanger.finalReviewResult.repository;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.QFinalReviewResult.*;

import java.util.List;

import org.springframework.data.domain.PageRequest;
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
	public Slice<FinalReviewResultListResponse> findAllFinalReviewResults(Long cursorId, Long userId, Integer size) {
		QFinalReviewResult finalReviewResult = QFinalReviewResult.finalReviewResult;

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
			.limit(size)
			.fetch();

		boolean hasNext = false;
		if (results.size() > size) {
			results.remove(size);
			hasNext = true;
		}

		return new SliceImpl<>(results, PageRequest.of(0, size), hasNext);
	}

	private BooleanExpression eqCursorId(Long cursorId) {
		if (cursorId != null) {
			return finalReviewResult.id.gt(cursorId);
		}
		return null;
	}
}
