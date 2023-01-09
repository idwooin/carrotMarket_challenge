package com.numble.backend.post.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class PostNotFoundException extends CarrotBusinessException {

	private static final String message = "게시물이 존재하지 않습니다.";
	private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

	public PostNotFoundException() { super(message, httpStatus); }

}
