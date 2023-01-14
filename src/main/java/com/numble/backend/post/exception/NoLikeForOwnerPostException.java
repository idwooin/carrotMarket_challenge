package com.numble.backend.post.exception;

import org.springframework.http.HttpStatus;

import com.numble.backend.common.exception.CarrotBusinessException;

public class NoLikeForOwnerPostException extends CarrotBusinessException {
	private static final String message = "게시물의 작성자는 좋아요를 누를 수 없습니다.";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	public NoLikeForOwnerPostException() {
		super(message, httpStatus);
	}
}
