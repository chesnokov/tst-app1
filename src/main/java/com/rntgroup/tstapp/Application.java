package com.rntgroup.tstapp;

import com.rntgroup.tstapp.repository.RepositoryException;
import com.rntgroup.tstapp.repository.UserTestRepository;
import com.rntgroup.tstapp.test.Question;
import com.rntgroup.tstapp.test.UserTest;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;
import java.util.Scanner;

public class Application {
	private final UserTestRepository repository;

	public Application(UserTestRepository userTestRepository) {
		this.repository = userTestRepository;
	}

	public void run() {
		List<UserTest> tests;
		try {
			tests = repository.findAll();
		} catch (RepositoryException e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
			return;
		}
		for(;;) {
			for(int i=0; i<tests.size(); i++) {
				System.out.println("" + (i+1) + ". " + tests.get(i).getName());
			}
			String input = getUserInput("Choose test or q: ");
			if(input.startsWith("q")) {
				break;
			} else {
				runTest(tests, Integer.parseInt(input));
			}
		}

	}

	private void runTest(List<UserTest> tests, int index)  {
		if(index < 1 || index >= tests.size())  {
			return;
		}
		UserTest userTest = tests.get(index-1);
		int correctCount = 0;
		for(Question question: userTest.getQuestions()) {
			boolean questionResult = getQuestionResult(question);
			if(questionResult) {
				correctCount++;
			}
		}
		System.out.println("Result: " + correctCount + " of " + userTest.getQuestions().size());
	}

	private boolean getQuestionResult(Question question) {
		System.out.println(question.getText());
		for(int i=0; i<question.getAnswers().size(); i++) {
			System.out.println("" + (i + 1) + ". " + question.getAnswers().get(i).getText());
		}
		String input = getUserInput("Answer: ");
		int answerIndex = Integer.parseInt(input);
		if (answerIndex >= 1 && answerIndex < question.getAnswers().size()) {
			return question.getAnswers().get(answerIndex - 1).isCorrect();
		} else {
			return false;
		}
	}

	private String getUserInput(String text) {
		System.out.print(text);
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine().trim();
	}

}
