package com.devcourse.ReviewRanger.openAI.constant;

public enum Command {

	COMMAND_FOR_CLEAN_REPLIES(
  """
		이 글들은 동료에 대한 평가 글이야.
		"," 로 구분된 구문들에 대해 욕설, 비난, 부정적 감 정 표현을 제거한 뒤 핵심만 간략하게 요약해서 최대한 짧은 하나의 구문을 만들어줘.
		"""
	);

	private String text;

	Command(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
