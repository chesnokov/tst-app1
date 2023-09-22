package com.rntgroup.tstapp.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputOutputService {
	private final PrintStream out;
	private final InputStream in;

	public InputOutputService(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
	}

	public void print(String str) {
		out.print(str);
	}

	public void println(String str) {
		out.println(str);
	}

	public String input() {
		Scanner scanner = new Scanner(in);
		return scanner.nextLine().trim();
	}

	public String getUserInput(String text) {
		print(text);
		return input();
	}
}
