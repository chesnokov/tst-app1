package com.rntgroup.tstapp.repository;

import com.rntgroup.tstapp.test.UserTest;

import java.util.ArrayList;
import java.util.List;

public class UserTestRepository {

	private final List<BaseRepository> repositories;

	public UserTestRepository(List<BaseRepository> repositories) {
		this.repositories = repositories;
	}

	public List<UserTest> findAll() throws RepositoryException {
		List<UserTest> userTests = new ArrayList<>();
		for(BaseRepository repository:repositories) {
			userTests.addAll(repository.findAll());
		}
		return userTests;
	}

}
