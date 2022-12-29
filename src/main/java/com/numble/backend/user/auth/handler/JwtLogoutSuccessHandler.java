package com.numble.backend.user.auth.handler;

import static com.numble.backend.common.dto.ResponseEnum.USER_LOGOUT_SUCCESS;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numble.backend.common.dto.Response;

public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		Response<Void> logoutResponse = new Response<>(USER_LOGOUT_SUCCESS, null);

		objectMapper.writeValue(response.getWriter(), logoutResponse);
	}
}
