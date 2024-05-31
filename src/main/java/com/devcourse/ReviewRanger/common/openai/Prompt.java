package com.devcourse.ReviewRanger.common.openai;

import com.devcourse.ReviewRanger.common.openai.constant.Command;

public class Prompt {

	private String target;

	private Command command;

	public Prompt(String target, Command command) {
		this.target = target;
		this.command = command;
	}

	public String generatePrompt() {
		return target.concat(" ").concat(command.getText());
	}
}
