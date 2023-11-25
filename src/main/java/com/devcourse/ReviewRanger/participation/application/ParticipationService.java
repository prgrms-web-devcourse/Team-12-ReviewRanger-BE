package com.devcourse.ReviewRanger.participation.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.request.UpdateParticipationRequest;
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
	private final ReplyTargetRepository replyTargetRepository;

	public ParticipationService(ParticipationRepository participationRepository,
		UserRepository userRepository,
		ReviewRepository reviewRepository, ReplyTargetService replyTargetService,
		ReplyTargetRepository replyTargetRepository) {
		this.participationRepository = participationRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.replyTargetService = replyTargetService;
		this.replyTargetRepository = replyTargetRepository;
	}

	@Transactional
	public boolean createParticipations(Long reviewId, List<Long> responserIds) {
		List<Participation> participations = responserIds.stream()
			.map(responserId -> {
				User responer = userRepository.findById(responserId)
					.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
				Participation participation = new Participation(responer);
				participation.assignReviewId(reviewId);

				return participation;
			}).toList();

		participationRepository.saveAll(participations);

		return true;
	}

	public Slice<GetParticipationResponse> getAllParticipationsByResponserOfCursorPaging(
		Long cursorId,
		Long responserId,
		Pageable pageable
	) {
		Slice<Participation> participations = participationRepository.findByResponserId(cursorId, responserId,
			pageable);

		List<GetParticipationResponse> getParticipationResponses = new ArrayList<>();
		for (Participation participation : participations.getContent()) {
			Long reviewId = participation.getReviewId();
			Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RangerException(NOT_FOUND_REPLY));
			String title = review.getTitle();

			GetParticipationResponse getParticipationResponse = new GetParticipationResponse(participation, title);
			getParticipationResponses.add(getParticipationResponse);
		}

		return new SliceImpl<>(getParticipationResponses, participations.getPageable(), participations.hasNext());
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

		participations.stream()
			.forEach(participation -> {
				if (!participation.getIsAnswered()) {
					throw new RangerException(NOT_FINISHED_PARTICIPANTS);
				}
				participation.changeStatus(ReviewStatus.DEADLINE);
			});
	}

	public List<ParticipationResponse> getAllParticipationOrThrow(Long reviewId, String name, String sort) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		User creator = userRepository.findById(review.getRequesterId())
			.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
		UserResponse creatorResponse = UserResponse.toResponse(creator);
		ReviewResponse reviewResponse = ReviewResponse.toResponse(review, creatorResponse);

		List<ParticipationResponse> responses = participationRepository.findAllByReviewIdToDynamic(review.getId(), name,
				sort)
			.stream()
			.map(participation -> ParticipationResponse.toResponse(participation, reviewResponse))
			.toList();

		return responses;
	}

	public List<ReceiverResponse> getAllReceiver(Long reviewId, String searchName) {
		return replyTargetRepository.findAllByParticipationIdToDynamic(
			reviewId, searchName);
	}

	@Transactional
	public void submitReplies(SubmitParticipationRequest request) {
		Participation participation = getByIdOrThrow(request.participationId());

		replyTargetService.createReviewTarget(participation.getId(), participation.getReviewId(),
			request.createReplyTargetRequests());

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
