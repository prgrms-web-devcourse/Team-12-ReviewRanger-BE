package com.devcourse.ReviewRanger.slack;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static com.devcourse.ReviewRanger.participation.domain.ReviewStatus.*;
import static com.devcourse.ReviewRanger.slack.SlackConstant.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;

@Service
@Transactional(readOnly = true)
public class SlackService {

	@Value(value = "${slack.token}")
	private String slackToken;

	private final ParticipationRepository participationRepository;
	private final ReviewRepository reviewRepository;

	public SlackService(ParticipationRepository participationRepository, ReviewRepository reviewRepository) {
		this.participationRepository = participationRepository;
		this.reviewRepository = reviewRepository;
	}

	public void sendSlackMessage(String message) {
		try {
			MethodsClient methods = Slack.getInstance().methods(slackToken);
			methods.chatPostMessage(req -> req.channel(TEST).text(message));
		} catch (SlackApiException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Async
	@Transactional
	public void alertSlackMessage(String title) {
		Review review = reviewRepository.findByTitleAndStatusOrderByCreateAtDesc(title, PROCEEDING)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));

		List<Participation> notAnswerResponsers
			= participationRepository.findAllByReviewIdAndIsAnswered(review.getId(), false);

		LocalDateTime reviewCloseTime = review.getClosedAt();
		LocalDateTime currentTime = LocalDateTime.now();
		long remainingTime = ChronoUnit.DAYS.between(currentTime, reviewCloseTime);

		StringBuilder message = new StringBuilder("피어리뷰 기간이 " + remainingTime + "일 남았습니다. ");
		message.append("마감일은 ").append(reviewCloseTime.toLocalDate()).append("입니다. \n");

		for (int i = 0; i < notAnswerResponsers.size(); i++) {
			Participation participation = notAnswerResponsers.get(i);
			User responser = participation.getResponser();

			String responserName = responser.getName();
			message.append("\"").append(responserName).append("\"");

			if (i == notAnswerResponsers.size() - 1) {
				message.append("님 ");
			} else {
				message.append("님, ");
			}
		}
		message.append("피어리뷰를 완료해 주세요!! 😆🥺");

		sendSlackMessage(message.toString());
	}
}
