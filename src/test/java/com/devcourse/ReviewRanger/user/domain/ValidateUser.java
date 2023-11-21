package com.devcourse.ReviewRanger.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.devcourse.ReviewRanger.common.config.QueryDslConfig;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
@DataJpaTest
class ValidateUser {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("user 필드엔 빈 값이 들어갈 수 없다.")
	void notValidateUser() {
		// given
		User userA = new User("", "", "");
		User userB = new User("장수연", " ", null);

		// when & then
		Assertions.assertThrows(ConstraintViolationException.class,
			() -> userRepository.save(userA));

		Assertions.assertThrows(ConstraintViolationException.class,
			() -> userRepository.save(userB));
	}

	@Test
	@DisplayName("user 필드에 유효한 필드 값을 넣을 수 있다.")
	void validateUser() {
		// given
		User user = new User("장수연", "dev1234@naver.com", "abc12341234");

		// when
		User saveUser = userRepository.save(user);

		// then
		assertNotNull(saveUser);
		assertEquals(user.getId(), saveUser.getId());
		assertEquals(user.getName(), saveUser.getName());
	}
}
