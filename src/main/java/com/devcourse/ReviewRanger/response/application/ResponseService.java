package com.devcourse.ReviewRanger.response.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.eachSurveyResult.application.EachSurveyResultService;
import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.QuestionResponseRequest;
import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.dto.reponse.QuestionResponse;
import com.devcourse.ReviewRanger.response.domain.Response;
import com.devcourse.ReviewRanger.response.dto.request.Answers;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponse;
import com.devcourse.ReviewRanger.response.dto.request.ResponseRequest;
import com.devcourse.ReviewRanger.response.dto.request.Results;
import com.devcourse.ReviewRanger.response.repository.ResponseRepository;
import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

@Service
@Transactional(readOnly = true)
public class ResponseService {

	private final QuestionService questionService;
	private final ResponseRepository responseRepository;

	public ResponseService(QuestionService questionService, ResponseRepository responseRepository) {
		this.questionService = questionService;
		this.responseRepository = responseRepository;
	}

	@Transactional
	public void submitResponses(EachSurveyResult createdEachSurveyResult,
		List<QuestionResponseRequest> questionResponseRequests) {

		for (QuestionResponseRequest questionResponseRequest : questionResponseRequests) {
			Long questionId = questionResponseRequest.questionId();
			Question question = questionService.getQuestion(questionId);

			List<ResponseRequest> responseRequests = questionResponseRequest.responseRequests();
			for (ResponseRequest responseRequest : responseRequests) {
				Response response = responseRequest.toEntity();
				response.setEachSurveyResult(createdEachSurveyResult);
				response.setQuestion(question);
				responseRepository.save(response);
			}
		}
	}

}
