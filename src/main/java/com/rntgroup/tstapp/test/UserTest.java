package com.rntgroup.tstapp.test;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserTest {
	private String name;
	private List<Question> questions;

	public UserTest(String name, List<Question> questions) {
		this.name = name;
		this.questions = questions;
	}
}
