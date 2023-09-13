package com.rntgroup.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.test.UserTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExternalUserTestRepository implements UserTestRepository {
	private final String userTestDir;
	private final CsvUserTestReader userTestReader;


	public ExternalUserTestRepository(String userTestDir, CsvUserTestReader userTestReader) {
		this.userTestDir = userTestDir;
		this.userTestReader = userTestReader;
	}

	public List<UserTest> findAll() throws RepositoryException {
		File directory = new File(userTestDir);
		File[] files = directory.listFiles();

		return Arrays.stream(Optional.ofNullable(files).orElse(new File[0]))
				.filter(f -> f.getName().endsWith(".csv"))
				.map(this::makeUserTest)
				.collect(Collectors.toList());
	}

	private UserTest makeUserTest(File file) throws RepositoryException {
		try(CSVReader csvReader = new CSVReader(new FileReader(file))) {
			return userTestReader.makeUserTest(file.toString(),csvReader);
		} catch (FileNotFoundException e) {
			throw new RepositoryException(MessageFormat.format("User test not found {0}", file.toString()), e);
		} catch (IOException e) {
			throw new RepositoryException(MessageFormat.format("Error reading user test file {0}", file.toString()), e);
		} catch (CsvValidationException e) {
			throw new RepositoryException(MessageFormat.format("Error in csv structure of user test file {0}", file.toString()), e);
		}
	}
}
