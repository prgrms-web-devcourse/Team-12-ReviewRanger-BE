package com.devcourse.ReviewRanger.slack;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slack")
public class SlackController {

	private final SlackService slackService;

	public SlackController(SlackService slackService) {
		this.slackService = slackService;
	}

	@PostMapping("/alert")
	public void alertMessage(@RequestParam("text") String text) {
		slackService.alertSlackMessage(text);
	}
}
