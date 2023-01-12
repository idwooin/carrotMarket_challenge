package com.numble.backend.chat.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class ChatRoomNotFoundException extends CarrotBusinessException {

	private static final String message = "채팅 방이 존재하지 않습니다.";
	private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

	public ChatRoomNotFoundException() {
		super(message, httpStatus);
	}
}
