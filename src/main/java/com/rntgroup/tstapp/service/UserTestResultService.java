package com.rntgroup.tstapp.service;

import com.rntgroup.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;

public class UserTestResultService {
	private final InputOutputService ioService;
	private final ConversionService conversionService;

	public UserTestResultService(InputOutputService ioService, ConversionService conversionService) {
		this.ioService = ioService;
		this.conversionService = conversionService;
	}

	public void processResult(UserTestResult result) {
		ioService.println(conversionService.convert(result, String.class));
	}
}
