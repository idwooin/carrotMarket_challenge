package com.numble.backend.common.exception;

import org.springframework.http.HttpStatus;

public class FileCountExceedException extends CarrotBusinessException{

	private static final String message = "파일 개수가 초과되었습니다.";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	public FileCountExceedException() {
		super(message, httpStatus);
	}
}
