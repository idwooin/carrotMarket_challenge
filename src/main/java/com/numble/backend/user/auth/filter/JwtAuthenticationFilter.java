package com.numble.backend.user.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.auth.application.RedisCachingService;
import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.auth.exception.UserUnauthorizedException;
import com.numble.backend.user.auth.jwt.JwtTokenFactory;
import com.numble.backend.user.auth.util.AuthorizationExtractor;
import com.numble.backend.user.auth.util.AuthorizationType;
import com.numble.backend.user.auth.vo.Role;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenFactory jwtTokenFactory;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private RedisCachingService redisCachingService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

		String accessToken = convert(request);
		log.info("[AccessToken] : {}", accessToken);

		if (isAlreadyLogout(accessToken)) {
			throw new UserUnauthorizedException();
		}

		if (isInvalidAccessToken(accessToken)) {
			SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication());
			filterChain.doFilter(wrappingRequest, wrappingResponse);
			wrappingResponse.copyBodyToResponse();
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(buildAuthentication(accessToken));
		log.info("[Authentication ?????? ??????]");

		filterChain.doFilter(wrappingRequest, wrappingResponse);
		wrappingResponse.copyBodyToResponse();
	}

	private boolean isInvalidAccessToken(String accessToken) {
		return !StringUtils.hasText(accessToken) || !jwtTokenFactory.isValidAccessToken(accessToken);
	}

	private boolean isAlreadyLogout(String accessToken) {
		return StringUtils.hasText(accessToken) && redisCachingService.getValues(accessToken) != null;
	}

	private String convert(HttpServletRequest request) {
		return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
	}

	private UsernamePasswordAuthenticationToken buildAuthentication(String accessToken) {
		String userIdFromToken = jwtTokenFactory.getUserIdFromToken(accessToken);
		Collection<GrantedAuthority> rolesFromToken = jwtTokenFactory.getRolesFromToken(accessToken);
		CustomUser findUser = userDetailsService.findUserForAuthentication(userIdFromToken);
		log.info("[User Info] : {}", findUser.getEmail());
		return new UsernamePasswordAuthenticationToken(findUser, "", rolesFromToken);
	}

	protected Authentication anonymousAuthentication() {
		CustomUser anonymousUser = new CustomUser("", "", "");
		anonymousUser.addRole(Role.ANONYMOUS);
		return new AnonymousAuthenticationToken("anonymous", anonymousUser, anonymousUser.getAuthorities());
	}
}
