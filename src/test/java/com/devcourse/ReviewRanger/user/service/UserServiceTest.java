package com.devcourse.ReviewRanger.user.service;

import static com.devcourse.ReviewRanger.user.service.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;

@Transactional
@SpringBootTest
class UserServiceTest {

	private final UserService userService;

	@Autowired
	public UserServiceTest(UserService userService) {
		this.userService = userService;
	}

	// TODO: 회원가입 테스트
	// @Test
	// void 회원가입_테스트_성공() {
	// 	// given
	// 	JoinRequest joinRequest = SUYEON_FIXTURE.toJoinRequest();
	//
	// 	// when
	// 	Boolean joinResult = userService.join(joinRequest);
	//
	// 	// then
	// 	assertTrue(joinResult);
	// }
}
