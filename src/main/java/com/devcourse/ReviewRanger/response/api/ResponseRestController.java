package com.devcourse.ReviewRanger.response.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.response.application.ResponseService;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponse;

@RestController
public class ResponseRestController {

	private final ResponseService responseService;

	public ResponseRestController(ResponseService responseService) {
		this.responseService = responseService;
	}

	@PostMapping(value = "/invited-surveys")
	public ResponseEntity<Boolean> create(@RequestBody @Valid CreateResponse request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(responseService.createResponse(request));
	}
}
