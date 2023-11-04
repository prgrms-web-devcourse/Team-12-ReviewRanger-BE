package com.devcourse.ReviewRanger.eachSurveyResult.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.CreateEachSurveyResultDto;
import com.devcourse.ReviewRanger.eachSurveyResult.repository.EachSurveyResultRepository;
import com.devcourse.ReviewRanger.response.application.ResponseService;

@Service
@Transactional(readOnly = true)
public class EachSurveyResultService {

	private final EachSurveyResultRepository eachSurveyResultRepository;
	private final ResponseService responseService;

	public EachSurveyResultService(EachSurveyResultRepository eachSurveyResultRepository,
		ResponseService responseService) {
		this.eachSurveyResultRepository = eachSurveyResultRepository;
		this.responseService = responseService;
	}

	@Transactional
	public void createEachSurveyResult(Long responserId, Long surveyResultId,
		List<CreateEachSurveyResultDto> eachSurveyResultDto) {
		for (CreateEachSurveyResultDto eachSurveyResult : eachSurveyResultDto) {
			Long reviewerId = eachSurveyResult.reviewerId();

			EachSurveyResult savedEachSurveyResultId = eachSurveyResultRepository.save(
				new EachSurveyResult(reviewerId, surveyResultId));

			responseService.createResponse(responserId, savedEachSurveyResultId.getId(), eachSurveyResult.responses());
		}
	}
}
