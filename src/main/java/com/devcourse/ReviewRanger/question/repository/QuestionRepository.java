package com.devcourse.ReviewRanger.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcourse.ReviewRanger.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}