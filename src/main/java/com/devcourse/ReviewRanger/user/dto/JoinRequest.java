package com.devcourse.ReviewRanger.user.dto;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.devcourse.ReviewRanger.user.domain.User;

public record JoinRequest(
	@Column(nullable = false, length = 20)
	@NotBlank(message = "이름은 빈값 일 수 없습니다.")
	@Pattern(regexp = NAME_REGEXP, message = "이름 형식이 맞지 않습니다.")
	String name,

	@Column(nullable = false, length = 30)
	@NotBlank(message = "이메일은 빈값 일 수 없습니다.")
	@Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 맞지 않습니다.")
	String email,

	@Column(nullable = false)
	@NotBlank(message = "비밀번호는 빈값 일 수 없습니다.")
	@Pattern(regexp = PASSWORD_REGEXP, message = "비밀번호 형식이 맞지 않습니다.")
	String password
) {
	public User toEntity(String password) {
		return new User(this.name, this.email, password);
	}
}
