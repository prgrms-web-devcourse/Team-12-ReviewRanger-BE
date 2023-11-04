package com.devcourse.ReviewRanger.surveyresult.application;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.eachSurveyResult.application.EachSurveyResultService;
import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.EachSurveyRequest;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponseDto;
import com.devcourse.ReviewRanger.survey.repository.SurveyRepository;
import com.devcourse.ReviewRanger.common.constant.Status;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.devcourse.ReviewRanger.surveyresult.dto.request.SubmitSurveyResultRequest;
import com.devcourse.ReviewRanger.surveyresult.dto.response.AllResponserResultResponseDto;
import com.devcourse.ReviewRanger.surveyresult.dto.response.Responsers;
import com.devcourse.ReviewRanger.surveyresult.dto.response.SurveyResultResponse;
import com.devcourse.ReviewRanger.surveyresult.repository.SurveyResultRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class SurveyResultService {

	private final EachSurveyResultService eachSurveyResultService;

	private final SurveyResultRepository surveyResultRepository;
	private final UserRepository userRepository;
	private final SurveyRepository surveyRepository;

	public SurveyResultService(EachSurveyResultService eachSurveyResultService,
		SurveyResultRepository surveyResultRepository, UserRepository userRepository,
		SurveyRepository surveyRepository) {
		this.eachSurveyResultService = eachSurveyResultService;
		this.surveyResultRepository = surveyResultRepository;
		this.userRepository = userRepository;
		this.surveyRepository = surveyRepository;
	}

	@Transactional
	public void createAllSurveyResults(List<SurveyResult> surveyResults) {
		surveyResultRepository.saveAll(surveyResults);
	}

	public List<SurveyResultResponse> getResponserSurveyResult(Long responserId) {

		List<SurveyResult> surveyResults = surveyResultRepository.findByResponserId(responserId);
		return surveyResults.stream()
			.map(surveyResult -> new SurveyResultResponse(surveyResult))
			.toList();
	}

	public SurveyResult findSurveyResult(Long surveyId, Long responserId) {
		return surveyResultRepository.findBySurveyIdAndResponserId(surveyId, responserId);
	}

	public Long getResponserCount(Long surveyId) {
		List<SurveyResult> surveyResults = surveyResultRepository.findBySurveyId(surveyId);

		return surveyResults.stream().filter(surveyResult -> surveyResult.getQuestionAnsweredStatus()).count();
	}

	@Transactional
	public Boolean closeSurveyResultOrThrow(Long surveyId) {
		List<SurveyResult> surveyResults = surveyResultRepository.findBySurveyId(surveyId);
		surveyResults.stream().forEach(surveyResult -> surveyResult.changeStatus(Status.END));

		return true;
	}

	public AllResponserResultResponseDto getAllReponserSurveyResult(Long surveyId, Long userId) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new NoSuchElementException("설문이 존재하지 않습니다."));

		SurveyResponseDto surveyResponseDto = new SurveyResponseDto(surveyId, survey.getTitle(),
			survey.getType(), survey.getCreateAt(), survey.getUpdatedAt());

		List<SurveyResult> surveyResults = surveyResultRepository.findBySurveyIdAndQuestionAnsweredStatusTrue(surveyId);

		ArrayList<Responsers> responserList = new ArrayList<>();
		AllResponserResultResponseDto allResponserResultResponseDto = new AllResponserResultResponseDto(
			surveyResults.size(), surveyResponseDto, responserList);

		for (SurveyResult surveyResult : surveyResults) {
			User user = userRepository.findById(surveyResult.getResponserId())
				.orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

			Responsers responser = new Responsers(surveyResult.getId(), user.getId(), user.getName(),
				surveyResult.getUpdatedAt());

			allResponserResultResponseDto.responsers().add(responser);
		}

		return allResponserResultResponseDto;
	}

	@Transactional
	public void submitSurveyResult(SubmitSurveyResultRequest submitSurveyResultRequest) {
		Long surveyResultId = submitSurveyResultRequest.surveyResultId();
		SurveyResult surveyResult = surveyResultRepository.findById(surveyResultId)
			.orElseThrow(EntityNotFoundException::new);

		List<EachSurveyRequest> eachSurveyRequests = submitSurveyResultRequest.eachSurveyRequests();
		eachSurveyResultService.submitSurveyResults(surveyResult,eachSurveyRequests)
		;
		surveyResult.changeStatus(Status.DEADLINE);
	}
}
