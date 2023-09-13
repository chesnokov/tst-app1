package com.rntgroup.tstapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@PropertySource("classpath:/app.properties")
public class ApplicationConfiguration {

	@Value("${test.dir}")
	private String userTestDirectory;

	@Bean
	public String userTestDirectory() {
		return userTestDirectory;
	}

	@Bean
	public InputStream systemInput() {
		return System.in;
	}

	@Bean
	public PrintStream systemOutput() {
		return System.out;
	}
}
