package com.devcourse.ReviewRanger.response.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.response.application.ResponseService;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponse;

@RestController
public class ResponseRestController {

	private final ResponseService responseService;

	public ResponseRestController(ResponseService responseService) {
		this.responseService = responseService;
	}

	@PostMapping(value = "/invited-surveys")
	public RangerResponse<Void> create(@RequestBody @Valid CreateResponse request) {
		Boolean response = responseService.createResponse(request);

		return RangerResponse.ok(response);
	}
}
