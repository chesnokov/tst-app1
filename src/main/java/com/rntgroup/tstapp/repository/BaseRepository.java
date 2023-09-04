package com.rntgroup.tstapp.repository;

import com.rntgroup.tstapp.test.UserTest;

import java.util.List;

public interface BaseRepository {
	List<UserTest> findAll() throws RepositoryException;
}
