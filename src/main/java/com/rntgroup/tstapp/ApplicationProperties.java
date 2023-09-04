package com.rntgroup.tstapp;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {
	public static final String TESTS_DIR = "tstdir";
	private final String path;
	private Properties properties;

	public ApplicationProperties(String path) {
		this.path = path;
	}

	public Properties getProperties() throws IOException {
		if(properties == null) {
			Properties p=new Properties();
			try(FileReader reader = new FileReader(path)) {
				p.load(reader);
			}
			properties = p;
		}
		return properties;
	}
}
