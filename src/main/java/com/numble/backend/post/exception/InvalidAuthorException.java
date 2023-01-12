package com.numble.backend.post.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class InvalidAuthorException extends CarrotBusinessException {
	private static final String message = "게시물의 작성자가 아닙니다.";
	private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

	public InvalidAuthorException() {
		super(message, httpStatus);
	}
}
