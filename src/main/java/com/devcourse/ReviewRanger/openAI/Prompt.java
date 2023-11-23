package com.devcourse.ReviewRanger.openAI;

import com.devcourse.ReviewRanger.openAI.constant.Command;

public class Prompt {

	private String target;

	private Command command;

	public Prompt(String target, Command command) {
		this.target = target;
		this.command = command;
	}

	public String generatePrompt(){
		return target.concat(" ").concat(command.getText());
	}
}
