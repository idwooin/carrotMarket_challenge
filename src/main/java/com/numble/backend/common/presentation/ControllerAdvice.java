package com.numble.backend.common.presentation;

import javax.validation.ConstraintViolationException;

import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.numble.backend.common.dto.ExceptionResponse;
import com.numble.backend.common.exception.CarrotBusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(CarrotBusinessException.class)
	public ResponseEntity<ExceptionResponse> businessException(final CarrotBusinessException e) {
		log.info("{} : {}", ((Exception)e).getClass().getSimpleName(), e.getMessage());
		return toResponseEntity(e.getClientMessage(), e.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleValidationException(final MethodArgumentNotValidException e) {
		final FieldError fieldError = e.getFieldErrors()
			.get(0);
		final String message = fieldError.getField() + " :" + fieldError.getDefaultMessage();
		return toResponseEntity(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
		final HttpMessageNotReadableException e) {
		return toResponseEntity("요청값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(
		final ConstraintViolationException e) {
		return toResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleUnexpectedException(final Exception e) {
		log.warn("Internal server error", e);
		return toResponseEntity("예상치 못한 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ExceptionResponse> toResponseEntity(final String message, final HttpStatus httpStatus) {
		final ExceptionResponse response = new ExceptionResponse(message);
		return ResponseEntity.status(httpStatus)
			.body(response);
	}

}

