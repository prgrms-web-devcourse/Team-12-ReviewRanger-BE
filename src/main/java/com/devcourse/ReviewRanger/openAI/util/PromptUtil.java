package com.devcourse.ReviewRanger.openAI.util;

import java.util.List;

public class PromptUtil {

	private String target;

	private String command;

	public PromptUtil() {
	}

	public void setTargetFromElement(String element) {
		this.target = element;
	}

	public void setTargetFromElements(List<String> elements) {
		this.target = String.join(", ", elements);
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String generateCommand() {
		return target.concat(" ").concat(command);
	}
}
