package com.numble.backend.user.auth.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class UserUnauthorizedException extends CarrotBusinessException {

	private static final String message = "해당 유저는 인증되지 않았습니다.";
	private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

	public UserUnauthorizedException() {
		super(message, httpStatus);
	}
}
