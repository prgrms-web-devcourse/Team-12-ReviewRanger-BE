package com.devcourse.ReviewRanger.response.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.response.domain.Response;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponseRequest;
import com.devcourse.ReviewRanger.response.repository.ResponseRepository;

@Service
@Transactional(readOnly = true)
public class ResponseService {

	private final ResponseRepository responseRepository;

	public ResponseService(ResponseRepository responseRepository) {
		this.responseRepository = responseRepository;
	}

	@Transactional
	public void createResponse(Long responserId, EachSurveyResult eachSurveyResult,
		List<CreateResponseRequest> responses) {
		for (CreateResponseRequest request : responses) {
			Long questionId = request.questionId();
			List<String> answerList = request.answerText();

			for (String answerText : answerList) {
				Response response = new Response(responserId, eachSurveyResult, questionId, answerText);
				responseRepository.save(response);
			}
		}
	}
}
