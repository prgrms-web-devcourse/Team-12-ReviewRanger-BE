package com.devcourse.ReviewRanger.participation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.participation.domain.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long>, ParticipationCustomRepository {

	List<Participation> findByResponserId(Long responserId);

	List<Participation> findByReviewId(Long reviewId);

	Optional<Participation> findByReviewIdAndResponserId(Long reviewId, Long responserId);

	List<Participation> findAllByReviewId(Long reviewId);

	List<Participation> findAllByReviewIdAndIsAnswered(Long reviewId, Boolean isAnswered);
}
