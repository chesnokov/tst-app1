package com.rntgroup.tstapp;

import com.rntgroup.tstapp.repository.CompositeUserTestRepository;
import com.rntgroup.tstapp.repository.UserTestRepositoryException;
import com.rntgroup.tstapp.test.Question;
import com.rntgroup.tstapp.test.UserTest;
import com.rntgroup.tstapp.test.UserTestResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.convert.ConversionService;

import java.util.List;

public class Application {
	private final CompositeUserTestRepository repository;
	private final InputOutputService ioService;
	private final ConversionService conversionService;
	private final UserTestResultService userTestResultService;

	public Application(CompositeUserTestRepository userTestRepository,
			InputOutputService ioService, ConversionService conversionService, UserTestResultService userTestResultService) {
		this.repository = userTestRepository;
		this.ioService = ioService;
		this.conversionService = conversionService;
		this.userTestResultService = userTestResultService;
	}

	public void run() {
		try {
			List<UserTest> tests = repository.findAll();
			listAndRunUserTests(tests);
		} catch (UserTestRepositoryException e) {
			ioService.println(ExceptionUtils.getStackTrace(e));
		}
	}

	private void listAndRunUserTests(List<UserTest> tests) {
		for(;;) {
			listUserTests(tests);
			String input = ioService.getUserInput("Choose test or q: ");
			if(input.startsWith("q")) {
				break;
			} else {
				int index = Integer.parseInt(input);
				if(index < 1 || index >= tests.size())  {
					continue;
				}
				runTest(tests.get(index - 1));
			}
		}
	}

	private void listUserTests(List<UserTest> tests) {
		ioService.print(conversionService.convert(tests, String.class));
	}

	private void runTest(UserTest userTest)  {
		int correctCount = 0;
		for(Question question: userTest.getQuestions()) {
			boolean questionResult = getQuestionResult(question);
			if(questionResult) {
				correctCount++;
			}
		}
		UserTestResult userTestResult = new UserTestResult(correctCount, userTest.getQuestions().size());
		userTestResultService.processResult(userTestResult);
	}

	private boolean getQuestionResult(Question question) {
		showQuestion(question);
		String input = ioService.getUserInput("Answer: ");
		int answerIndex = Integer.parseInt(input);
		if (answerIndex >= 1 && answerIndex < question.getAnswers().size()) {
			return question.getAnswers().get(answerIndex - 1).isCorrect();
		} else {
			return false;
		}
	}

	private void showQuestion(Question question) {
		ioService.print(conversionService.convert(question, String.class));
	}
}
