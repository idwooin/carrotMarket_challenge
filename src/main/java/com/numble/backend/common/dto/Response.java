package com.numble.backend.common.dto;

import lombok.Getter;

@Getter
public class Response<T> {
	private final String code;
	private final String message;
	private final T data;

	public Response(ResponseEnum responseEnum, T data) {
		this(responseEnum.getCode(), responseEnum.getMessage(), data);
	}

	public Response(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
