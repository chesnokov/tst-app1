package com.rntgroup.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.test.UserTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class InternalUserTestRepository implements BaseRepository {
	private final String userTestDir;
	private final String userTestSuffix;
	private final CsvUserTestReader userTestReader;

	public InternalUserTestRepository(String userTestDir, String userTestSuffix, CsvUserTestReader userTestReader) {
		this.userTestDir = userTestDir;
		this.userTestSuffix = userTestSuffix;
		this.userTestReader = userTestReader;
	}

	public List<UserTest> findAll() throws RepositoryException {
		String jarName = new File(InternalUserTestRepository.class.getResource("/beans.xml").getFile()).getParent().replaceAll("(!|file:\\\\)", "");
		if(jarName.endsWith(".jar")) {
			return getTstFilesFromJar(jarName);
		}
		return getTstFilesFromFileSystem();
	}

	private List<UserTest> getTstFilesFromJar(String jarName) throws RepositoryException {
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
			throw new RepositoryException(MessageFormat.format("Error reading {0}", jarName), e);
		}
		return tstFiles;
	}

	private List<UserTest> getTstFilesFromFileSystem() throws RepositoryException {
		File directory = new File(InternalUserTestRepository.class.getResource("/" + userTestDir).getFile());
		List<UserTest> tstFiles = new ArrayList<>();
		File[] files = directory.listFiles();
		if(files != null) {
			for (File entry : files ) {
				if (entry.getName().endsWith(".csv")) {
					tstFiles.add(makeUserTest("/" + userTestDir + "/" + entry.getName()));
				}
			}
		}
		return tstFiles;
	}

	private UserTest makeUserTest(String path) throws RepositoryException {
		try(CSVReader csvReader = new CSVReader(new InputStreamReader(InternalUserTestRepository.class.getResourceAsStream(path)))) {
			return userTestReader.makeUserTest(path, csvReader);
		} catch (IOException e) {
			throw new RepositoryException(MessageFormat.format("Error reading internal resource {0}", path), e);
		} catch (CsvValidationException e) {
			throw new RepositoryException(MessageFormat.format("Error reading csv {0}", path), e);
		}
	}

}
