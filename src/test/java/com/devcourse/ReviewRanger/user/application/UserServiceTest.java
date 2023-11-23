package com.devcourse.ReviewRanger.user.application;

import static com.devcourse.ReviewRanger.user.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	void 회원정보_이름_수정_성공() {
		// given
		User user = SUYEON_FIXTURE.toEntity();
		String editName = "editName";
		when(userRepository.existsByName(editName)).thenReturn(false);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		// when
		userService.updateName(1L, editName);

		// then
		verify(userRepository, times(1)).existsByName(editName);
		verify(userRepository, times(1)).findById(1L);
	}

	@Test
	void 회원정보_이름_수정_실패() {
		// given
		String editName = "editName";
		when(userRepository.existsByName(editName)).thenReturn(true);

		// when & then
		assertThrows(RangerException.class,
			() -> userService.updateName(1L, editName)
		);
	}

	@Test
	void 회원정보_비밀번호_수정_성공() {
		// given
		User user = SUYEON_FIXTURE.toEntity();
		String editPassword = "editPassword";
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(editPassword)).thenReturn("encodedPassword");

		// when
		userService.updatePassword(1L, editPassword);

		// then
		verify(userRepository, times(1)).findById(1L);
		assertNotEquals(user.getPassword(), editPassword);
	}

	@Test
	@DisplayName("존재하지 않는 사용자의 비밀번호를 변경하는 경우")
	void 회원정보_비밀번호_수정_실패_없는_사용자() {
		// given
		String editPassword = "editPassword";
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		// when & then
		assertThrows(RangerException.class,
			() -> userService.updateName(99L, editPassword)
		);
	}
}
