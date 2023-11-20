package com.devcourse.ReviewRanger.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.user.application.UserService;

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
