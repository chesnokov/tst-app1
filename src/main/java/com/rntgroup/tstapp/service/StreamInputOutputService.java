package com.rntgroup.tstapp.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

public class StreamInputOutputService implements InputOutputService {
	private final PrintStream out;
	private final InputStream in;

	public StreamInputOutputService(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
	}

	public void print(String str) {
		out.print(str);
	}

	public void println(String str) {
		out.println(str);
	}

	private String input() {
		Scanner scanner = new Scanner(in);
		return scanner.nextLine().trim();
	}

	public String getUserInput(String text) {
		print(text);
		return input();
	}

	@Override
	public Optional<Integer> getUserInputAsInt(String text) {
		print(text);
		try {
			return Optional.of(Integer.parseInt(input()));
		} catch(NumberFormatException e) {
			return Optional.empty();
		}
	}
}
