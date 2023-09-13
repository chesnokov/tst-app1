package com.rntgroup.tstapp.conversion;

import com.rntgroup.tstapp.test.UserTestResult;
import org.springframework.core.convert.converter.Converter;

public class UserTestResultConverter implements Converter<UserTestResult, String> {
	@Override
	public String convert(UserTestResult result) {
		return "Result: " + result.getCorrectAnswers() + " of " + result.getAnswersCount();
	}
}
