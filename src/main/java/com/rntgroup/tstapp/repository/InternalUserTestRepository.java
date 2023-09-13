package com.rntgroup.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.test.UserTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class InternalUserTestRepository implements UserTestRepository {
	private final String userTestDir;
	private final String userTestSuffix;
	private final CsvUserTestReader userTestReader;

	public InternalUserTestRepository(String userTestDir, String userTestSuffix, CsvUserTestReader userTestReader) {
		this.userTestDir = userTestDir;
		this.userTestSuffix = userTestSuffix;
		this.userTestReader = userTestReader;
	}

	public List<UserTest> findAll() {
		URL resource = InternalUserTestRepository.class.getResource("/anyfile");
		if(isNull(resource)) {
			throw new UserTestRepositoryException("Resource file '/anyfile' not found");
		}
		String fileName = resource.getFile();
		String jarName = new File(fileName)
				.getParent()
				.replaceAll("(!|file:\\\\)", "");

		if(jarName.endsWith(".jar")) {
			return getTstFilesFromJar(jarName);
		}
		return getTstFilesFromFileSystem();
	}

	private List<UserTest> getTstFilesFromJar(String jarName) {
		List<UserTest> tstFiles = new ArrayList<>();
		try (JarFile jf = new JarFile(jarName)){

			Enumeration<JarEntry> entries = jf.entries();
			while (entries.hasMoreElements()) {
				JarEntry je = entries.nextElement();
				if (je.getName().startsWith(userTestDir) &&
					je.getName().endsWith(userTestSuffix)) {
					tstFiles.add(makeUserTest("/"+je.getName()));
				}
			}
		} catch (IOException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error reading user test files list from {0}", jarName), e);
		}
		return tstFiles;
	}

	private List<UserTest> getTstFilesFromFileSystem() {
		File directory = new File(InternalUserTestRepository.class.getResource("/" + userTestDir).getFile());
		File[] files = directory.listFiles();

		return Arrays.stream(Optional.ofNullable(files).orElse(new File[0]))
				.filter(f -> f.getName().endsWith(".csv"))
				.map(f -> "/" + userTestDir + "/" + f.getName())
				.map(this::makeUserTest)
				.collect(Collectors.toList());
	}

	private UserTest makeUserTest(String path) {
		try(CSVReader csvReader = new CSVReader(new InputStreamReader(InternalUserTestRepository.class.getResourceAsStream(path)))) {
			return userTestReader.makeUserTest(path, csvReader);
		} catch (IOException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error reading user test file from internal resource {0}", path), e);
		} catch (CsvValidationException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error in csv structure of user test file {0}", path), e);
		}
	}

}
