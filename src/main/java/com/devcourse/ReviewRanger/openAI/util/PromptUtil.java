package com.devcourse.ReviewRanger.openAI.util;

import java.util.List;

public class PromptUtil {

	private String target;

	private String command;

	public PromptUtil(List<String> elements, String command) {
		this.target = generateTargetFromElements(elements);
		this.command = command;
	}

	private String generateTargetFromElements(List<String> elements) {
		return String.join(", ", elements);
	}

	public String generateCommand() {
		return target.concat(" ").concat(command);
	}
}
