package com.devcourse.ReviewRanger.response.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.response.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.response.domain.Response;
import com.devcourse.ReviewRanger.response.dto.request.Answers;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponse;
import com.devcourse.ReviewRanger.response.dto.request.Results;
import com.devcourse.ReviewRanger.response.repository.EachSurveyResultRepository;
import com.devcourse.ReviewRanger.response.repository.ResponseRepository;
import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

@Service
@Transactional(readOnly = true)
public class ResponseService {

	private final ResponseRepository responseRepository;
	private final EachSurveyResultRepository eachSurveyResultRepository;
	private final SurveyResultService surveyResultService;

	public ResponseService(ResponseRepository responseRepository,
		EachSurveyResultRepository eachSurveyResultRepository, SurveyResultService surveyResultService) {
		this.responseRepository = responseRepository;
		this.eachSurveyResultRepository = eachSurveyResultRepository;
		this.surveyResultService = surveyResultService;
	}

	@Transactional
	public Boolean createResponse(CreateResponse request) {
		Long responserId = request.responserId();
		Long surveyResultId = getSurveyResult(request, responserId).getId();

		for (Results result : request.results()) {
			Long reviewerId = result.reviewerId();

			EachSurveyResult eachSurveyResult = new EachSurveyResult(reviewerId, surveyResultId);
			Long savedEachSurveyResultId = eachSurveyResultRepository.save(eachSurveyResult).getId();

			for (Answers answer : result.answers()) {
				Long questionId = answer.questionId();
				String questionType = answer.questionType().getDisplayName();
				List<String> list = answer.answer();

				switch (questionType) {//오버로딩 적용하기
					case "주관식" -> saveSubjective(responserId, savedEachSurveyResultId, questionId, list.get(0));
					case "객관식_중복없음" ->
						saveUniqueObjective(responserId, savedEachSurveyResultId, questionId, list.get(0));
					case "객관식_중복있음" -> saveDuplicateObjective(responserId, savedEachSurveyResultId, questionId, list);
				}
			}
		}

		return true;
	}

	private SurveyResult getSurveyResult(CreateResponse request, Long responserId) {
		return surveyResultService.findSurveyResult(request.surveyId(), responserId);
	}

	private void saveSubjective(Long responserId, Long savedEachSurveyResultId, Long questionId, String answer) {
		Response response = new Response(responserId, savedEachSurveyResultId, questionId, answer);
		responseRepository.save(response);
	}

	private void saveUniqueObjective(Long responserId, Long savedEachSurveyResultId, Long questionId, String answer) {
		Response response = new Response(responserId, savedEachSurveyResultId, questionId, Long.parseLong(answer));
		responseRepository.save(response);
	}

	private void saveDuplicateObjective(Long responserId, Long savedEachSurveyResultId, Long questionId,
		List<String> answers) {
		for (String answer : answers) {
			Response response = new Response(responserId, savedEachSurveyResultId, questionId, Long.parseLong(answer));
			responseRepository.save(response);
		}
	}
}
