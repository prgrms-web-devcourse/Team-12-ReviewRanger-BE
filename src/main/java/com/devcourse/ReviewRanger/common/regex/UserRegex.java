package com.devcourse.ReviewRanger.common.regex;

public class UserRegex {
	public static final String NAME_REGEXP = "^[가-힣a-zA-Z\\d\\[\\]<>]{2,20}";
	public static final String EMAIL_REGEXP = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
	public static final String PASSWORD_REGEXP = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$";
}
