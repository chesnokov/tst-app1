package com.rntgroup.tstapp.test;

import lombok.Getter;

@Getter
public class UserTestResult {

	private final int correctAnswers;
	private final int answersCount;

	public UserTestResult(int correctAnswers, int answersCount) {
		this.correctAnswers = correctAnswers;
		this.answersCount = answersCount;
	}
}
