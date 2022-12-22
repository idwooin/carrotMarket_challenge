package com.numble.backend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CarrotBusinessException extends RuntimeException {
	private final String clientMessage;
	private final HttpStatus httpStatus;

	protected CarrotBusinessException(final String clientMessage, final HttpStatus httpStatus) {
		super(clientMessage);
		this.clientMessage = clientMessage;
		this.httpStatus = httpStatus;
	}
}
