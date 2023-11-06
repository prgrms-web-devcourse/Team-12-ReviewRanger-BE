package com.devcourse.ReviewRanger.participation.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.domain.DeadlineStatus;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.AllResponserParticipateInReviewResponse;
import com.devcourse.ReviewRanger.participation.dto.response.ResponserResponse;
import com.devcourse.ReviewRanger.participation.dto.response.SubjectResponse;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.response.ReviewResponseDto;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.reviewedTarget.application.ReviewedTargetService;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.reviewedTarget.repository.ReviewedTargetRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ParticipationService {

	private final ParticipationRepository participationRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewedTargetRepository reviewedTargetRepository;
	private final ReviewedTargetService reviewedTargetService;

	public ParticipationService(ParticipationRepository participationRepository, UserRepository userRepository,
		ReviewRepository reviewRepository, ReviewedTargetRepository reviewedTargetRepository,
		ReviewedTargetService reviewedTargetService) {
		this.participationRepository = participationRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.reviewedTargetRepository = reviewedTargetRepository;
		this.reviewedTargetService = reviewedTargetService;
	}

	@Transactional
	public boolean createParticipations(Long reviewId, List<Long> responserIds) {
		List<Participation> participations = responserIds.stream().map(responserId -> {
			Participation participation = new Participation(responserId);
			participation.assignReviewId(reviewId);
			return participation;
		}).toList();

		participationRepository.saveAll(participations);

		return true;
	}

	public List<Participation> getResponserSurveyResult(Long responserId) {
		return participationRepository.findByResponserId(responserId);
	}

	public Long getResponserCount(Long reviewId) {
		List<Participation> participations = participationRepository.findByReviewId(reviewId);

		return participations.stream().filter(surveyResult -> surveyResult.getQuestionAnsweredStatus()).count();
	}

	@Transactional
	public Boolean closeParticipationOrThrow(Long reviewId) {
		List<Participation> participations = participationRepository.findByReviewId(reviewId);
		participations.stream().forEach(surveyResult -> surveyResult.changeStatus(DeadlineStatus.END));

		return true;
	}

	public AllResponserParticipateInReviewResponse getAllReponserParticipateInSurveyOrThrow(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RangerException(NOT_FOUND_SURVEY));

		ReviewResponseDto reviewResponseDto = new ReviewResponseDto(reviewId, review.getTitle(), review.getType(),
			review.getCreateAt(), review.getUpdatedAt());

		List<Participation> participations = participationRepository.findByReviewIdAndQuestionAnsweredStatusTrue(
			reviewId);

		ArrayList<ResponserResponse> responserList = new ArrayList<>();
		AllResponserParticipateInReviewResponse allResponserParticipateInsurveyDto = new AllResponserParticipateInReviewResponse(
			participations.size(), reviewResponseDto, responserList);

		for (Participation participation : participations) {
			User user = userRepository.findById(participation.getResponserId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			ResponserResponse responser = new ResponserResponse(participation.getId(), user.getId(), user.getName(),
				participation.getUpdatedAt());

			allResponserParticipateInsurveyDto.responsers().add(responser);
		}

		return allResponserParticipateInsurveyDto;
	}

	public List<SubjectResponse> getAllRecipientParticipateInSurveyOrThrow(Long surveyId) {
		List<SubjectResponse> responses = new ArrayList<>();
		Map<Long, List<Long>> subjectToResponsersMap = new HashMap<>();

		List<Participation> participations = participationRepository.findByReviewId(surveyId);

		for (Participation participation : participations) {
			List<ReviewedTarget> reviewedTargets = reviewedTargetRepository.findByParticipationId(
				participation.getId());

			for (ReviewedTarget reviewedTarget : reviewedTargets) {
				Long subjectId = reviewedTarget.getSubjectId();
				List<Long> responers = subjectToResponsersMap.getOrDefault(subjectId, new ArrayList<>());
				responers.add(participation.getResponserId());
				subjectToResponsersMap.put(subjectId, responers);
			}
		}

		for (Map.Entry<Long, List<Long>> recipient : subjectToResponsersMap.entrySet()) {
			User user = userRepository.findById(recipient.getKey())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			SubjectResponse response = new SubjectResponse(recipient.getKey(), user.getName(),
				recipient.getValue().size(), recipient.getValue());
			responses.add(response);
		}

		return responses;
	}

	@Transactional
	public Boolean submitResponse(SubmitParticipationRequest request) {
		Participation participation = participationRepository.findById(request.participationId())
			.orElseThrow(() -> new RangerException(NOT_FOUND_SURVEY_RESULT));

		reviewedTargetService.createReviewTarget(participation.getResponserId(), participation.getId(),
			request.createReviewedTargetRequests());

		participation.changeStatus(DeadlineStatus.DEADLINE);

		return true;
	}

}
