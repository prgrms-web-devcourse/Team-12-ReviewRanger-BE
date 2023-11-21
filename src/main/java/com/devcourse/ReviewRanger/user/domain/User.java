package com.devcourse.ReviewRanger.user.domain;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	public enum Role {
		ROLE_USER,
		ROLE_ADMIN
	}

	@Column(nullable = false, length = 20)
	@NotBlank(message = "이름은 빈값 일 수 없습니다.")
	@Pattern(regexp = NAME_REGEXP, message = "이름 형식이 맞지 않습니다.")
	private String name;

	@Column(nullable = false, length = 30)
	@NotBlank(message = "이메일은 빈값 일 수 없습니다.")
	@Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 맞지 않습니다.")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "비밀번호는 빈값 일 수 없습니다.")
	private String password;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Role role;

	@Column(name = "image_url")
	private String imageUrl;

	protected User() {
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = Role.ROLE_USER;
	}

	public void updateName(String editName) {
		this.name = editName;
	}

	public void updatePassword(String editEncodedPassword) {
		this.password = editEncodedPassword;
	}

	public void updateImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
