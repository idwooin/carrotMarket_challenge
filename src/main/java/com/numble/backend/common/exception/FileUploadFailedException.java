package com.numble.backend.common.exception;

import org.springframework.http.HttpStatus;

public class FileUploadFailedException extends CarrotBusinessException{
	private static final String message = "파일 업로드에 실패하였습니다.";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	public FileUploadFailedException() {
		super(message, httpStatus);
	}

	public FileUploadFailedException(String newMessage) {
		super(newMessage, httpStatus);
	}
}
