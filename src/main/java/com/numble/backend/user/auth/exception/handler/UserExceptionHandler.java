package com.numble.backend.user.auth.exception.handler;

import static com.numble.backend.common.dto.ResponseEnum.USER_DUPLICATED;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.numble.backend.common.dto.Response;
import com.numble.backend.common.dto.ResponseEnum;
import com.numble.backend.user.auth.exception.UserAlreadyExistsException;
import com.numble.backend.user.auth.exception.UserNicknameDuplicatedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<Response<Void>> duplicatedUser(UserAlreadyExistsException exception) {
		log.info("[UserAlreadyExistsException] Handler calling");
		return new ResponseEntity(new Response<>(USER_DUPLICATED.getCode(), USER_DUPLICATED.getMessage(), null), HttpStatus.CONFLICT);
	}

	@ExceptionHandler
	public ResponseEntity<Response<Void>> duplicatedNickname(UserNicknameDuplicatedException exception) {
		log.info("[UserNicknameDuplicatedException] Handler calling");
		return new ResponseEntity(new Response<>(USER_DUPLICATED.getCode(), exception.getMessage(), null), HttpStatus.CONFLICT);
	}
}
