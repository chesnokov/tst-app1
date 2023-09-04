package com.rntgroup.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.test.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCsvUserTestReader {
	private CsvUserTestReader userTestReader;

	@BeforeEach
	public void setUp() {
		userTestReader = new CsvUserTestReader();
	}

	@Test
	public void shouldMakeEmptyUserTest() throws IOException, CsvValidationException {
		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test2.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			assertEquals("testName", userTest.getName());
			assertEquals(0, userTest.getQuestions().size());
		}
	}

	@Test
	public void shouldMakeUserTest() throws IOException, CsvValidationException {
		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test1.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			assertEquals("testName", userTest.getName());
			assertEquals(2, userTest.getQuestions().size());
			assertEquals("Question1", userTest.getQuestions().get(0).getText());
			assertEquals(2, userTest.getQuestions().get(0).getAnswers().size());
			assertEquals("Answer1", userTest.getQuestions().get(0).getAnswers().get(0).getText());
			assertEquals("Answer2", userTest.getQuestions().get(0).getAnswers().get(1).getText());
			assertFalse(userTest.getQuestions().get(0).getAnswers().get(0).isCorrect());
			assertTrue(userTest.getQuestions().get(0).getAnswers().get(1).isCorrect());
			assertEquals("Question2", userTest.getQuestions().get(1).getText());
			assertEquals(3, userTest.getQuestions().get(1).getAnswers().size());
			assertEquals("Answer1", userTest.getQuestions().get(1).getAnswers().get(0).getText());
			assertEquals("Answer2", userTest.getQuestions().get(1).getAnswers().get(1).getText());
			assertEquals("Answer3", userTest.getQuestions().get(1).getAnswers().get(2).getText());
			assertTrue(userTest.getQuestions().get(1).getAnswers().get(0).isCorrect());
			assertFalse(userTest.getQuestions().get(1).getAnswers().get(1).isCorrect());
			assertFalse(userTest.getQuestions().get(1).getAnswers().get(2).isCorrect());
		}

	}
}
