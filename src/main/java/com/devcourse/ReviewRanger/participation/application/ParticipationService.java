package com.devcourse.ReviewRanger.participation.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.request.UpdateParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.CursorResponse;
import com.devcourse.ReviewRanger.participation.dto.response.GetParticipationResponse;
import com.devcourse.ReviewRanger.participation.dto.response.ParticipationResponse;
import com.devcourse.ReviewRanger.participation.dto.response.ReceiverResponse;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.response.ReviewResponse;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.dto.UserResponse;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ParticipationService {

	private final ParticipationRepository participationRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final ReplyTargetService replyTargetService;

	public ParticipationService(ParticipationRepository participationRepository, UserRepository userRepository,
		ReviewRepository reviewRepository, ReplyTargetService replyTargetService) {
		this.participationRepository = participationRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.replyTargetService = replyTargetService;
	}

	@Transactional
	public boolean createParticipations(Long reviewId, List<Long> responserIds) {
		List<Participation> participations = responserIds.stream()
			.map(responserId -> {
				Participation participation = new Participation(responserId);
				participation.assignReviewId(reviewId);
				return participation;
			}).toList();

		participationRepository.saveAll(participations);

		return true;
	}

	public CursorResponse<GetParticipationResponse> getAllParticipationsByResponser(
		Long cursorId,
		Long responserId,
		Integer size
	) {
		Slice<Participation> participations  = participationRepository.findByResponserId(cursorId, responserId, size);
		Long nextCursor = participations.getContent().get(participations.getContent().size()-1).getId();

		List<GetParticipationResponse> getParticipationResponses = new ArrayList<>();
		for (Participation participation : participations.getContent()) {
			Long reviewId = participation.getReviewId();
			Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RangerException(NOT_FOUND_REPLY));
			String title = review.getTitle();

			GetParticipationResponse getParticipationResponse = new GetParticipationResponse(participation, title);
			getParticipationResponses.add(getParticipationResponse);
		}

		return new CursorResponse<>(getParticipationResponses, participations.hasNext(), nextCursor);
	}

	public Long getResponserCount(Long reviewId) {
		List<Participation> participations = getAllByReviewId(reviewId);

		return participations.stream()
			.filter(participation -> participation.getIsAnswered())
			.count();
	}

	@Transactional
	public void closeParticipationOrThrow(Long reviewId) {
		List<Participation> participations = getAllByReviewId(reviewId);
		participations.stream().forEach(participation -> participation.changeStatus(ReviewStatus.END));
	}

	public List<ParticipationResponse> getAllParticipation(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		User creator = userRepository.findById(review.getRequesterId())
			.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
		UserResponse creatorResponse = UserResponse.toResponse(creator);
		ReviewResponse reviewResponse = ReviewResponse.toResponse(review, creatorResponse);

		List<ParticipationResponse> responses = participationRepository.findByReviewId(review.getId())
			.stream()
			.map(participation -> {
				User responser = userRepository.findById(participation.getResponserId())
					.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
				UserResponse responserResponse = UserResponse.toResponse(responser);

				return ParticipationResponse.toResponse(participation, responserResponse, reviewResponse);
			})
			.toList();

		return responses;
	}

	public List<ReceiverResponse> getAllReceiverParticipateInReviewOrThrow(Long reviewId) {
		List<ReceiverResponse> responses = new ArrayList<>();
		Map<Long, Integer> receiverToResponsersMap = new HashMap<>();

		List<Participation> participations = getAllByReviewId(reviewId);

		for (Participation participation : participations) {
			List<ReplyTarget> replyTargets = replyTargetService.getAllByParticipationId(participation.getId());

			for (ReplyTarget replyTarget : replyTargets) {
				Long receiverId = replyTarget.getReceiverId();
				receiverToResponsersMap.put(receiverId, receiverToResponsersMap.getOrDefault(receiverId, 0) + 1);
			}
		}

		for (Map.Entry<Long, Integer> receiver : receiverToResponsersMap.entrySet()) {
			User user = userRepository.findById(receiver.getKey())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			ReceiverResponse response = new ReceiverResponse(receiver.getKey(), user.getName(),
				receiver.getValue());

			responses.add(response);
		}

		return responses;
	}

	@Transactional
	public void submitResponse(SubmitParticipationRequest request) {
		Participation participation = getByIdOrThrow(request.participationId());

		replyTargetService.createReviewTarget(participation.getId(), request.createReplyTargetRequests());

		participation.answeredReview();
	}

	@Transactional
	public void updateResponse(UpdateParticipationRequest request) {
		replyTargetService.updateReviewTarget(request.updateReplyTargetRequests());
	}

	public Participation getByIdOrThrow(Long id) {
		return participationRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_PARTICIPATION));
	}

	public List<Participation> getAllByReviewId(Long reviewId) {
		return participationRepository.findByReviewId(reviewId);
	}
}
