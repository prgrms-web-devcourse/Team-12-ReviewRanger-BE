package com.devcourse.ReviewRanger.response.api;

import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.response.application.ResponseService;

@RestController
public class ResponseRestController {

	private final ResponseService responseService;

	public ResponseRestController(ResponseService responseService) {
		this.responseService = responseService;
	}

}
