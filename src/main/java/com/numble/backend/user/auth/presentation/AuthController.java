package com.numble.backend.user.auth.presentation;

import static com.numble.backend.common.dto.ResponseEnum.USER_DELETE_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.USER_SIGNUP_SUCCESS;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.numble.backend.common.dto.Response;
import com.numble.backend.user.auth.annotation.CurrentUser;
import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.auth.presentation.dto.UserRegisterRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final CustomUserDetailsService userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<Response<Void>> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
		userDetailsService.register(userRegisterRequest);
		return new ResponseEntity<>(new Response<>(USER_SIGNUP_SUCCESS, null), HttpStatus.CREATED);
	}

	@DeleteMapping
	public ResponseEntity<Response<Void>> deleteUser(@CurrentUser CustomUser currentUser) {
		userDetailsService.delete(currentUser.getId());
		return new ResponseEntity<>(new Response<>(USER_DELETE_SUCCESS, null), HttpStatus.OK);
	}
}
