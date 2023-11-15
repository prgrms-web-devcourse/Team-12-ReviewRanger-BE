package com.devcourse.ReviewRanger.openAI.constant;

public class Command {

	public static final String COMMAND_FOR_CLEAN_REPLIES = """
		위의 컴마로 구분된 문장들을 각각 정제 한 뒤 합친 글을 보여줘.
		이 글들은 동료에 대한 평가 글이야
		따라서 욕설, 비난을 자연스럽게 제거해줘.
		또한 부정적이고 감정적인 표현도 제거해줘.
		윗사람이 아랫사람 평가하는 듯한 문장도 순화해줘. 
		""";
}
