package com.rntgroup.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.test.Answer;
import com.rntgroup.tstapp.test.Question;
import com.rntgroup.tstapp.test.UserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestCsvUserTestReader {
	private CsvUserTestReader userTestReader;

	@BeforeEach
	public void setUp() {
		userTestReader = new CsvUserTestReader();
	}

	@Test
	public void shouldMakeEmptyUserTest() throws IOException, CsvValidationException {

		UserTest expectedUserTest = new UserTest("testName", new ArrayList<>());

		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test2.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			Assertions.assertThat(userTest).isEqualToComparingFieldByFieldRecursively(expectedUserTest);
		}
	}

	@Test
	public void shouldMakeUserTest() throws IOException, CsvValidationException {

		List<Question> expectedQuestions = new ArrayList<>();
		List<Answer> answers1 = new ArrayList<>();
		answers1.add(new Answer("Answer1", false));
		answers1.add(new Answer("Answer2", true));
		expectedQuestions.add(new Question("Question1", answers1));
		List<Answer> answers2 = new ArrayList<>();
		answers2.add(new Answer("Answer1", true));
		answers2.add(new Answer("Answer2", false));
		answers2.add(new Answer("Answer3", false));
		expectedQuestions.add(new Question("Question2", answers2));
		UserTest expectedUserTest = new UserTest("testName", expectedQuestions);

		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test1.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			Assertions.assertThat(userTest).isEqualToComparingFieldByFieldRecursively(expectedUserTest);
		}
	}
}
