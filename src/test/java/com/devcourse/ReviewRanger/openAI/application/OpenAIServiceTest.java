package com.devcourse.ReviewRanger.openAI.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.devcourse.ReviewRanger.openAI.constant.Command;

class OpenAIServiceTest {


	@Test
	public void test(){
		//System.out.println(Command.COMMAND_FOR_CLEAN_REPLIES.getText());
		List<String> replies = List.of();

		String join = String.join(", ", replies);
		System.out.println(join);
	}
}