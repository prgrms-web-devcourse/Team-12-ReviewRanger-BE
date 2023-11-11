package com.devcourse.ReviewRanger.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByReviewId(Long reviewId);

	Optional<Question> findByTitleAndType(String title, QuestionType type);
}
