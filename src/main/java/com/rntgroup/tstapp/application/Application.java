package com.rntgroup.tstapp.application;

import com.rntgroup.tstapp.repository.UserTestRepository;
import com.rntgroup.tstapp.repository.UserTestRepositoryException;
import com.rntgroup.tstapp.service.InputOutputService;
import com.rntgroup.tstapp.service.UserTestResultService;
import com.rntgroup.tstapp.test.Question;
import com.rntgroup.tstapp.test.UserTest;
import com.rntgroup.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;

import java.text.MessageFormat;
import java.util.List;

public class Application {
	private final UserTestRepository repository;
	private final InputOutputService ioService;
	private final ConversionService conversionService;
	private final UserTestResultService userTestResultService;

	public Application(UserTestRepository userTestRepository,
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
			ioService.println(
				MessageFormat.format("Error during user tests loading: {0}",e.getMessage()));
		}
	}

	private void listAndRunUserTests(List<UserTest> tests) {
		boolean runUserTest = true;
		while(runUserTest) {
			listUserTests(tests);
			String input = ioService.getUserInput("Choose test or q: ");
			if(input.startsWith("q")) {
				runUserTest = false;
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
			boolean questionResult = askQuestion(question);
			if(questionResult) {
				correctCount++;
			}
		}
		UserTestResult userTestResult = new UserTestResult(userTest.getName(),
			correctCount, userTest.getQuestions().size());
		userTestResultService.processResult(userTestResult);
	}

	private boolean askQuestion(Question question) {
		showQuestion(question);
		int answerIndex = ioService.getUserInputAsInt("Answer: ", 0);
		return isAnswerCorrect(question, answerIndex);
	}

	private boolean isAnswerCorrect(Question question, int answerIndex) {
		return answerIndex >= 1 && answerIndex < question.getAnswers().size() &&
			question.getAnswers().get(answerIndex - 1).isCorrect();
	}

	private void showQuestion(Question question) {
		ioService.print(conversionService.convert(question, String.class));
	}
}
