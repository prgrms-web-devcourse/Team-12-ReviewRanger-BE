package com.devcourse.ReviewRanger.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devcourse.ReviewRanger.auth.dto.JoinRequest;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.common.redis.RedisUtil;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@InjectMocks
	private AuthService authService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private RedisUtil redisUtil;

	@Test
	void 회원가입_성공_테스트() {
		// given
		JoinRequest mockRequest = mock(JoinRequest.class);
		when(mockRequest.password()).thenReturn("mockPassword");
		when(mockRequest.toEntity(anyString())).thenReturn(mock(User.class));
		when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

		User savedUser = mock(User.class);

		when(userRepository.save(any(User.class))).thenReturn(savedUser);
		when(savedUser.getId()).thenReturn(1L);

		// when
		Boolean result = authService.join(mockRequest);

		// then
		verify(passwordEncoder, times(1)).encode("mockPassword");
		verify(userRepository, times(1)).save(any(User.class));
		assertTrue(result);
	}

	@Test
	void 회원가입_실패_이메일중복_테스트() {
		// given
		JoinRequest joinRequest = new JoinRequest("장수연", "dev12341@devcource.com", "abc12341234");
		when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
		when(userRepository.existsByEmail(anyString())).thenReturn(true);

		// when & then
		assertThrows(RangerException.class,
			() -> authService.join(joinRequest)
		);
	}

	@Test
	void 회원가입_실패_이름중복_테스트() {
		// given
		JoinRequest joinRequest = new JoinRequest("장수연", "dev12341@devcource.com", "abc12341234");
		when(passwordEncoder.encode(any())).thenReturn("encryptedPassword");
		when(userRepository.existsByName(anyString())).thenReturn(true);

		// when & then
		assertThrows(RangerException.class,
			() -> authService.join(joinRequest)
		);
	}

	@Test
	void 로그아웃_성공_테스트() {
		// given
		String mockToken = "Bearer mockToken";
		doNothing().when(redisUtil).setBlackList(anyString(), anyString(), anyInt());

		// when
		authService.logout(mockToken);

		// then
		verify(redisUtil, times(1)).setBlackList(anyString(), anyString(), anyInt());
	}
}
