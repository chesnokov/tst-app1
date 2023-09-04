package com.rntgroup.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.ApplicationProperties;
import com.rntgroup.tstapp.test.UserTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.rntgroup.tstapp.ApplicationProperties.TESTS_DIR;

public class ExternalUserTestRepository implements BaseRepository {
	private final ApplicationProperties conf;
	private final CsvUserTestReader userTestReader;


	public ExternalUserTestRepository(ApplicationProperties conf, CsvUserTestReader userTestReader) {
		this.conf = conf;
		this.userTestReader = userTestReader;
	}

	public List<UserTest> findAll() throws RepositoryException {
		File directory;
		try {
			directory = new File(conf.getProperties().getProperty(TESTS_DIR));
		} catch (IOException e) {
			throw new RepositoryException("Error reading configuration", e);
		}
		List<UserTest> tstFiles = new ArrayList<>();
		File[] files = directory.listFiles();
		if(files != null) {
			for (File entry : files ) {
				if (entry.getName().endsWith(".csv")) {
					tstFiles.add(makeUserTest(entry));
				}
			}
		}
		return tstFiles;
	}

	private UserTest makeUserTest(File file) throws RepositoryException {
		try(CSVReader csvReader = new CSVReader(new FileReader(file))) {
			return userTestReader.makeUserTest(file.toString(),csvReader);
		} catch (FileNotFoundException e) {
			throw new RepositoryException(MessageFormat.format("File not found {0}", file.toString()), e);
		} catch (IOException e) {
			throw new RepositoryException(MessageFormat.format("Error reading file {0}", file.toString()), e);
		} catch (CsvValidationException e) {
			throw new RepositoryException(MessageFormat.format("Error in csv file {0}", file.toString()), e);
		}
	}
}
