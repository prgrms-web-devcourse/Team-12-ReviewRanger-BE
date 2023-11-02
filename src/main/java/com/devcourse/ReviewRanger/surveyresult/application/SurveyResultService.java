package com.devcourse.ReviewRanger.surveyresult.application;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponseDto;
import com.devcourse.ReviewRanger.survey.repository.SurveyRepository;
import com.devcourse.ReviewRanger.surveyresult.domain.DeadlineStatus;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.devcourse.ReviewRanger.surveyresult.dto.response.AllResponserResultResponseDto;
import com.devcourse.ReviewRanger.surveyresult.dto.response.Responsers;
import com.devcourse.ReviewRanger.surveyresult.repository.SurveyResultRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class SurveyResultService {

	private final SurveyResultRepository surveyResultRepository;
	private final UserRepository userRepository;
	private final SurveyRepository surveyRepository;

	public SurveyResultService(SurveyResultRepository surveyResultRepository, UserRepository userRepository,
		SurveyRepository surveyRepository) {
		this.surveyResultRepository = surveyResultRepository;
		this.userRepository = userRepository;
		this.surveyRepository = surveyRepository;
	}

	@Transactional
	public void createSurveyResult(Long surveyId, List<SurveyResult> surveyResults) {
		surveyResults.forEach(surveyResult -> surveyResult.assignSurveyId(surveyId));
		surveyResultRepository.saveAll(surveyResults);
	}

	public List<SurveyResult> getResponserSurveyResult(Long responserId) {
		return surveyResultRepository.findByResponserId(responserId);
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
		surveyResults.stream().forEach(surveyResult -> surveyResult.changeStatus(DeadlineStatus.END));

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
}
