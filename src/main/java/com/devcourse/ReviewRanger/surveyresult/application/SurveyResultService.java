package com.devcourse.ReviewRanger.surveyresult.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.eachSurveyResult.repository.EachSurveyResultRepository;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponseDto;
import com.devcourse.ReviewRanger.survey.repository.SurveyRepository;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.devcourse.ReviewRanger.surveyresult.dto.response.AllRecipientParticipateInSurveyDto;
import com.devcourse.ReviewRanger.surveyresult.dto.response.AllResponserParticipateInSurveyDto;
import com.devcourse.ReviewRanger.surveyresult.dto.response.Recipients;
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
	private final EachSurveyResultRepository eachSurveyResultRepository;

	public SurveyResultService(SurveyResultRepository surveyResultRepository, UserRepository userRepository,
		SurveyRepository surveyRepository, EachSurveyResultRepository eachSurveyResultRepository) {
		this.surveyResultRepository = surveyResultRepository;
		this.userRepository = userRepository;
		this.surveyRepository = surveyRepository;
		this.eachSurveyResultRepository = eachSurveyResultRepository;
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

		return surveyResults.stream()
			.filter(surveyResult -> surveyResult.getQuestionAnsweredStatus())
			.count();
	}

	public AllResponserParticipateInSurveyDto getAllReponserParticipateInSurveyOrThrow(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_SURVEY));

		SurveyResponseDto surveyResponseDto = new SurveyResponseDto(surveyId, survey.getTitle(),
			survey.getType(), survey.getCreateAt(), survey.getUpdatedAt());

		List<SurveyResult> surveyResults = surveyResultRepository.findBySurveyIdAndQuestionAnsweredStatusTrue(surveyId);

		ArrayList<Responsers> responserList = new ArrayList<>();
		AllResponserParticipateInSurveyDto allResponserParticipateInsurveyDto = new AllResponserParticipateInSurveyDto(
			surveyResults.size(), surveyResponseDto, responserList);

		for (SurveyResult surveyResult : surveyResults) {
			User user = userRepository.findById(surveyResult.getResponserId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			Responsers responser = new Responsers(surveyResult.getId(), user.getId(), user.getName(),
				surveyResult.getUpdatedAt());

			allResponserParticipateInsurveyDto.responsers().add(responser);
		}

		return allResponserParticipateInsurveyDto;
	}

	public AllRecipientParticipateInSurveyDto getAllRecipientParticipateInSurveyOrThrow(Long surveyId) {
		AllRecipientParticipateInSurveyDto allRecipientParticipateInSurveyDto = new AllRecipientParticipateInSurveyDto(
			new ArrayList<>());
		Map<Long, List<Long>> subjectToResponsersMap = new HashMap<>();

		List<SurveyResult> surveyResults = surveyResultRepository.findBySurveyId(surveyId);

		for (SurveyResult surveyResult : surveyResults) {
			List<EachSurveyResult> eachSurveyResults = eachSurveyResultRepository.findBySurveyResultId(
				surveyResult.getId());

			for (EachSurveyResult eachSurveyResult : eachSurveyResults) {
				Long subjectId = eachSurveyResult.getSubjectId();
				List<Long> responers = subjectToResponsersMap.getOrDefault(subjectId, new ArrayList<>());
				responers.add(surveyResult.getResponserId());
				subjectToResponsersMap.put(subjectId, responers);
			}
		}

		for (Map.Entry<Long, List<Long>> recipient : subjectToResponsersMap.entrySet()) {
			User user = userRepository.findById(recipient.getKey())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			Recipients response = new Recipients(recipient.getKey(), user.getName(), recipient.getValue().size(),
				recipient.getValue());
			allRecipientParticipateInSurveyDto.recipients().add(response);
		}

		return allRecipientParticipateInSurveyDto;
	}
}
