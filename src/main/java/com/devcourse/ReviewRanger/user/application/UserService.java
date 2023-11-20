package com.devcourse.ReviewRanger.user.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.common.image.infrastructure.S3manager;
import com.devcourse.ReviewRanger.common.jwt.JwtTokenProvider;
import com.devcourse.ReviewRanger.common.redis.RedisUtil;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.dto.LoginRequest;
import com.devcourse.ReviewRanger.user.dto.LoginResponse;
import com.devcourse.ReviewRanger.user.dto.UserInfoResponse;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Component
@Transactional(readOnly = true)
public class UserService {
	private final static String DIRECTORY = "ranger-image";

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisUtil redisUtil;
	private final S3manager s3Manager;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
		AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider,
		RedisUtil redisUtil, S3manager s3Manager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.redisUtil = redisUtil;
		this.s3Manager = s3Manager;
	}

	@Transactional
	public Boolean join(JoinRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPassword);

		if (!isNotExistName(request.name())) {
			throw new RangerException(EXIST_SAME_NAME);
		}

		if (!isNotExistEmail(request.email())) {
			throw new RangerException(EXIST_SAME_EMAIL);
		}

		return (userRepository.save(newUser).getId()) > 0;
	}

	public LoginResponse login(LoginRequest request) {
		// user 검증
		UsernamePasswordAuthenticationToken authenticationToken
			= new UsernamePasswordAuthenticationToken(request.email(), request.password());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// token 생성
		String accessToken = jwtTokenProvider.createToken(authentication);

		return new LoginResponse(accessToken, "Bearer");
	}

	public boolean isNotExistName(String name) {
		return !userRepository.existsByName(name);
	}

	public boolean isNotExistEmail(String email) {
		return !userRepository.existsByEmail(email);
	}

	@Transactional
	public void updateName(Long id, String editName) {
		if (!isNotExistName(editName)) {
			throw new RangerException(EXIST_SAME_NAME);
		}

		User user = getUserOrThrow(id);
		user.updateName(editName);
	}

	@Transactional
	public void updateImage(Long id, MultipartFile multipartFile) throws IOException {
		User user = getUserOrThrow(id);
		String fileName = user.getId() + multipartFile.getOriginalFilename();

		s3Manager.delete(fileName, DIRECTORY);
		String uploadImageUrl = s3Manager.upload(multipartFile, DIRECTORY, fileName);

		user.updateImage(uploadImageUrl);
	}

	@Transactional
	public void updatePassword(Long id, String editEncodedPassword) {
		User user = getUserOrThrow(id);
		editEncodedPassword = passwordEncoder.encode(editEncodedPassword);
		user.updatePassword(editEncodedPassword);
	}

	public void logout(String accessToken) {
		accessToken = getAccessToken(accessToken);
		redisUtil.setBlackList(accessToken, "accessToken", 5);
	}

	private String getAccessToken(String accessToken) {
		String[] tokenSplit = accessToken.split("\\s");
		return tokenSplit[1];
	}

	public User getUserOrThrow(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new RangerException(FAIL_USER_LOGIN));
	}

	public List<GetUserResponse> getAllUsers() {
		return userRepository.findAll().stream()
			.map(user -> new GetUserResponse(user.getId(), user.getName()))
			.toList();
	}

	public UserInfoResponse getUserInfo(UserPrincipal user) {
		Long id = user.getId();
		String name = user.getName();
		String email = user.getUsername();

		return new UserInfoResponse(id, name, email);
	}
}
