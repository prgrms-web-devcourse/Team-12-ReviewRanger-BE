package com.devcourse.ReviewRanger.question.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionResponse;

@RestController
public class QuestionController {

	private final QuestionService questionServuce;

	public QuestionController(QuestionService questionServuce) {
		this.questionServuce = questionServuce;
	}

	@GetMapping("/reviews/{reviewid}/questions")
	public RangerResponse<List<GetQuestionResponse>> getAllQuestionsByReview(
		@PathVariable("reviewid") Long reviewId
	) {
		List<GetQuestionResponse> getQuestionResponses = questionServuce.getAllQuestionsByReview(reviewId);

		return RangerResponse.ok(getQuestionResponses);
	}
}
