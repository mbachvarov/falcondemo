package com.falcon.springboot.falcondemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputDataException extends RuntimeException {
	private static final long serialVersionUID = -6559811087822419201L;
	private String inputData;
	private String expectedType;

	public InvalidInputDataException(String inputData, String expectedType) {
		super(String.format("%s is not valid '%s'", inputData, expectedType));
		this.inputData = inputData;
		this.expectedType = expectedType;
	}

	public String getInputData() {
		return inputData;
	}

	public String getExpectedType() {
		return expectedType;
	}
}