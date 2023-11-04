package com.devcourse.ReviewRanger.eachSurveyResult.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.EachSurveyRequest;
import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.QuestionResponseRequest;
import com.devcourse.ReviewRanger.eachSurveyResult.repository.EachSurveyResultRepository;
import com.devcourse.ReviewRanger.response.application.ResponseService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

@Service
@Transactional(readOnly = true)
public class EachSurveyResultService {

	private final ResponseService responseService;

	private final EachSurveyResultRepository eachSurveyResultRepository;

	public EachSurveyResultService(ResponseService responseService,
		EachSurveyResultRepository eachSurveyResultRepository) {
		this.responseService = responseService;
		this.eachSurveyResultRepository = eachSurveyResultRepository;
	}

	@Transactional
	public Long createEachSurveyResult(EachSurveyResult eachSurveyResult) {
		return eachSurveyResultRepository.save(eachSurveyResult).getId();
	}

	@Transactional
	public void submitSurveyResults(SurveyResult surveyResult, List<EachSurveyRequest> eachSurveyRequests) {
		for (EachSurveyRequest eachSurveyRequest : eachSurveyRequests) {
			EachSurveyResult eachSurveyResult = eachSurveyRequest.toEntity();
			eachSurveyResult.setSurveyResult(surveyResult);
			EachSurveyResult createdEachSurveyResult = eachSurveyResultRepository.save(eachSurveyResult);

			List<QuestionResponseRequest> questionResponseRequests = eachSurveyRequest.questionResponseRequests();
			responseService.submitResponses(createdEachSurveyResult, questionResponseRequests);
		}
	}

}
