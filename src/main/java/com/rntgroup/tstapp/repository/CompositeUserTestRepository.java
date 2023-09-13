package com.rntgroup.tstapp.repository;

import com.rntgroup.tstapp.test.UserTest;

import java.util.ArrayList;
import java.util.List;

public class CompositeUserTestRepository implements UserTestRepository {

	private final List<UserTestRepository> repositories;

	public CompositeUserTestRepository(List<UserTestRepository> repositories) {
		this.repositories = repositories;
	}

	public List<UserTest> findAll()  {
		List<UserTest> userTests = new ArrayList<>();
		for(UserTestRepository repository:repositories) {
			userTests.addAll(repository.findAll());
		}
		return userTests;
	}

}
