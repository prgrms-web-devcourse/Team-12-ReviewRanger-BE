package com.devcourse.ReviewRanger.slack;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/slack")
@Tag(name = "slack", description = "슬랙 API")
public class SlackController {

	private final SlackService slackService;

	public SlackController(SlackService slackService) {
		this.slackService = slackService;
	}

	@Tag(name = "slack")
	@Operation(summary = "슬랙 알림 전송", description = "피어리뷰 알림을 전송하는 슬랙 API", responses = {
		@ApiResponse(responseCode = "200", description = "슬랙 알림 전송 성공"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 피어리뷰 제목을 입력한 경우")
	})
	@PostMapping("/alert")
	public void alertMessage(@RequestParam("text") String title) {
		slackService.alertSlackMessage(title);
	}
}
