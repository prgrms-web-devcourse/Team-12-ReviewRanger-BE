package com.devcourse.ReviewRanger.reply.api;

import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.reply.application.ReplyService;

@RestController
public class ReplyRestController {

	private final ReplyService replyService;

	public ReplyRestController(ReplyService replyService) {
		this.replyService = replyService;
	}

}
