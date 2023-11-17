package com.devcourse.ReviewRanger.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.devcourse.ReviewRanger.review.domain.QReview;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Slice<Review> findByRequesterId(Long cursorId, Long requesterId, Integer size) {
		QReview review = QReview.review;

		BooleanBuilder where = new BooleanBuilder();
		Optional.ofNullable(requesterId).ifPresent(key -> where.and(review.requesterId.eq(key)));
		Optional.ofNullable(cursorId).ifPresent(key -> where.and(review.id.gt(cursorId)));

		List<Review> reviews = jpaQueryFactory
			.selectFrom(review)
			.where(where)
			.limit(size + 1)
			.fetch();

		boolean hasNext = reviews.size() > size;
		if (hasNext) {
			reviews.remove(reviews.size() - 1);
		}

		return new SliceImpl<>(reviews, PageRequest.of(0, size), hasNext);
	}
}
