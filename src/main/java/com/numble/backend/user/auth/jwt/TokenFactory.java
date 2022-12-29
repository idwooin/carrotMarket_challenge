package com.numble.backend.user.auth.jwt;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;

public interface TokenFactory {

	// token 발급
	JwtToken issue(String userId, List<GrantedAuthority> roles);

	// access token 발급
	String createAccessToken(String userId, List<GrantedAuthority> roles);

	// refresh token 발급
	String createRefreshToken(String userId);

	// refresh token 유효성 검사
	boolean isValidRefreshToken(String refreshToken);

	// access token 유효성 검사
	boolean isValidAccessToken(String accessToken);

	// header에서 토큰 추출
	JwtToken getTokenFromHeader(HttpServletRequest request);

	// token에서 id 추출
	String getUserIdFromToken(String accessToken);

	// token에서 role 추출
	Collection<GrantedAuthority> getRolesFromToken(String accessToken);
}
