package com.numble.backend.user.auth.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import com.numble.backend.user.auth.application.CustomUserDetailsService;
import com.numble.backend.user.auth.token.LoginAuthenticationToken;
import com.numble.backend.user.auth.vo.UserContext;

public class LoginAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String email = authentication.getName();
		String password = (String) authentication.getCredentials();

		UserContext accountContext = (UserContext) userDetailsService.loadUserByUsername(email);

		if (!passwordEncoder.matches(password, accountContext.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		return new LoginAuthenticationToken(accountContext.getUser(), null, accountContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(LoginAuthenticationToken.class);
	}
}
