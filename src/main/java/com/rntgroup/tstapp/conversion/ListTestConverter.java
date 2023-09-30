package com.rntgroup.tstapp.conversion;

import com.rntgroup.tstapp.test.UserTest;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class ListTestConverter implements Converter<List<UserTest>, String> {
	@Override
	public String convert(List<UserTest> tests) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<tests.size(); i++) {
			stringBuilder.append((i+1)).append(". ").append(tests.get(i).getName()).append("\n");
		}
		return stringBuilder.toString();
	}
}
