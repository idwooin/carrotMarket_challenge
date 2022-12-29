package com.numble.backend.user.auth.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class UserEmailDuplicatedException extends CarrotBusinessException {
	private static final String message = "이메일이 중복되었습니다.";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;


	public UserEmailDuplicatedException() {
		super(message, httpStatus);
	}
}
