package com.devcourse.ReviewRanger.response.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.response.domain.Response;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponseDto;
import com.devcourse.ReviewRanger.response.repository.ResponseRepository;

@Service
@Transactional(readOnly = true)
public class ResponseService {

	private final ResponseRepository responseRepository;

	public ResponseService(ResponseRepository responseRepository) {
		this.responseRepository = responseRepository;
	}

	@Transactional
	public void createResponse(Long ResponserId, Long eachSurveyResultId, List<CreateResponseDto> responses) {
		for (CreateResponseDto response : responses) {
			Long questionId = response.questionId();
			String questionType = response.questionType().getDisplayName();
			List<String> list = response.answer();

			switch (questionType) {//오버로딩 적용하기
				case "주관식" -> saveSubjective(ResponserId, eachSurveyResultId, questionId, list.get(0));
				case "객관식_중복없음" -> saveUniqueObjective(ResponserId, eachSurveyResultId, questionId,
					list.get(0));
				case "객관식_중복있음" -> saveDuplicateObjective(ResponserId, eachSurveyResultId, questionId,
					list);
			}
		}
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
