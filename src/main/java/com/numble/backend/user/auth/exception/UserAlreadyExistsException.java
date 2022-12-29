package com.numble.backend.user.auth.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class UserAlreadyExistsException extends CarrotBusinessException {
	private static final String message = "유저가 이미 존재합니다.";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	public UserAlreadyExistsException() {
		super(message, httpStatus);
	}
}
