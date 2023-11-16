package com.devcourse.ReviewRanger.participation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.QParticipation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ParticipationCustomRepositoryImpl implements ParticipationCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public ParticipationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Slice<Participation> findByResponserId(Long cursorId, Long responserId, Integer size) {
		QParticipation participation = QParticipation.participation;

		BooleanBuilder where = new BooleanBuilder();
		Optional.ofNullable(responserId).ifPresent(key -> where.and(participation.responserId.eq(key)));
		Optional.ofNullable(cursorId).ifPresent(key -> where.and(participation.id.gt(cursorId)));

		List<Participation> participations = jpaQueryFactory
			.selectFrom(QParticipation.participation)
			.where(where)
			.limit(size + 1)
			.fetch();

		boolean hasNext = participations.size() > size;
		if (hasNext) {
			participations.remove(participations.size() - 1);
		}

		return new SliceImpl<>(participations, PageRequest.of(0, size), hasNext);
	}
}
