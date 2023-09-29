package com.rntgroup.tstapp.service;

public interface InputOutputService {
	void print(String str);
	void println(String str);
	String getUserInput(String text);
	int getUserInputAsInt(String text, int errorInput);
}
