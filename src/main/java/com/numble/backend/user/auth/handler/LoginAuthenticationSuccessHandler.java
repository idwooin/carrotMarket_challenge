package com.numble.backend.user.auth.handler;

import static com.numble.backend.common.dto.ResponseEnum.USER_LOGIN_SUCCESS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numble.backend.common.dto.Response;
import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.auth.jwt.JwtToken;
import com.numble.backend.user.auth.jwt.JwtTokenFactory;

// LoginAuthenticatoinFilter에서 인증 성공 시 넘어오는 handler
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private JwtTokenFactory tokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		CustomUser user = (CustomUser) authentication.getPrincipal();

		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		List<GrantedAuthority> collect = user.getAuthorities()
			.stream()
			.map(userRole -> userRole.getAuthority())
			.collect(Collectors.toSet())
			.stream().map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		JwtToken jwtToken = JwtToken.builder()
			.accessToken(tokenProvider.createAccessToken(user.getId(), collect))
			.refreshToken(tokenProvider.createRefreshToken(user.getId()))
			.build();

		Response<JwtToken> tokenResponse = new Response<>(USER_LOGIN_SUCCESS, jwtToken);

		objectMapper.writeValue(response.getWriter(), tokenResponse);
	}
}
