package com.numble.backend.user.auth.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class UserNotFoundException extends CarrotBusinessException {
	private static final String message = "유저가 존재하지 않습니다.";
	private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;


	public UserNotFoundException() {
		super(message, httpStatus);
	}

}
