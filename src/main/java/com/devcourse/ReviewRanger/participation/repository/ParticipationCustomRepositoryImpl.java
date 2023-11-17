package com.devcourse.ReviewRanger.participation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.QParticipation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ParticipationCustomRepositoryImpl implements ParticipationCustomRepository {

	private static final String SUBMIT_AT = "submitAt";
	private static final String RESPONSER_NAME = "responserName";

	private final JPAQueryFactory jpaQueryFactory;

	QParticipation qParticipation = QParticipation.participation;

	public ParticipationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public List<Participation> findAllByReviewIdToDynamic(Long reviewId, String searchName, String sort) {
		BooleanBuilder builder = new BooleanBuilder();
		if (reviewId != null) {
			builder.and(qParticipation.reviewId.eq(reviewId));
		}

		if (StringUtils.hasText(searchName)) {
			builder.and(qParticipation.responser.name.contains(searchName));
		}

		List<Participation> participations = jpaQueryFactory.selectFrom(qParticipation)
			.where(builder)
			.orderBy(makeOrdersSpecifiers(sort))
			.fetch();

		return participations;
	}

	private OrderSpecifier<?> makeOrdersSpecifiers(String sort) {
		if (sort == null) {
			return new OrderSpecifier(Order.DESC, qParticipation.submitAt);
		}

		if (sort.equalsIgnoreCase(SUBMIT_AT)) {
			return new OrderSpecifier(Order.ASC, qParticipation.submitAt);
		}

		if (sort.equalsIgnoreCase(RESPONSER_NAME)) {
			return new OrderSpecifier(Order.ASC, qParticipation.responser.name);
		}

		return null;
	}

	@Override
	public Slice<Participation> findByResponserId(Long cursorId, Long responserId, Pageable pageable) {
		QParticipation participation = QParticipation.participation;
		int pageSize = pageable.getPageSize();

		BooleanBuilder where = new BooleanBuilder();
		Optional.ofNullable(responserId).ifPresent(key -> where.and(participation.responser.id.eq(key)));
		Optional.ofNullable(cursorId).ifPresent(key -> where.and(participation.id.gt(cursorId)));

		List<Participation> participations = jpaQueryFactory
			.selectFrom(participation)
			.where(where)
			.limit(pageSize + 1)
			.fetch();

		boolean hasNext = participations.size() > pageSize;
		if (hasNext) {
			participations.remove(pageSize);
		}

		return new SliceImpl<>(participations, PageRequest.of(0, pageSize), hasNext);
	}
}
