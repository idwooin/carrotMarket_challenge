package com.numble.backend.user.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.numble.backend.user.auth.application.RedisCachingService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenFactory implements TokenFactory{

	private final RedisCachingService redisCachingService;
	private final Key accessPrivateKey;
	private final Key refreshPrivateKey;
	@Value("${jwt.expire-length}")
	private Long accessExpirationMillis;
	@Value("${jwt.refresh-length}")
	private Long refreshExpirationMillis;

	private Key getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
	}

	public JwtTokenFactory(
		@Value("${jwt.access.private}") String accessPrivateKey,
		@Value("${jwt.refresh.private}") String refreshPrivateKey,
		RedisCachingService redisCachingService)
		throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.accessPrivateKey = getPrivateKey(accessPrivateKey);
		this.refreshPrivateKey = getPrivateKey(refreshPrivateKey);
		this.redisCachingService = redisCachingService;
	}

	@Override
	public JwtToken issue(String userId, List<GrantedAuthority> roles) {
		JwtToken token = JwtToken.builder()
			.accessToken(createAccessToken(userId, roles))
			.refreshToken(createRefreshToken(userId))
			.build();

		return token;
	}

	@Override
	public String createAccessToken(String userId, List<GrantedAuthority> roles) {
		Map<String, Object> claims = new HashMap<>();
		List<String> roleList = roles.stream()
			.map(role -> role.getAuthority())
			.collect(Collectors.toList());

		claims.put("userId", userId);
		claims.put("roles", roleList);

		Date createdDate = new Date();
		Date expirationDate = new Date(createdDate.getTime() + accessExpirationMillis);

		return Jwts.builder()
			// userId를 key로
			.setSubject(userId)
			// claim 넣기
			.setClaims(claims)
			// 발급 날짜
			.setIssuedAt(createdDate)
			// 유효 기간
			.setExpiration(expirationDate)
			// server의 accessPrivateKey로 서명
			.signWith(accessPrivateKey)
			// 압축
			.compact();
	}

	@Override
	public String createRefreshToken(String userId) {
		Date createdDate = new Date();
		Date expirationDate = new Date(createdDate.getTime() + refreshExpirationMillis);

		String refreshToken = Jwts.builder()
			.setIssuedAt(createdDate)
			.setExpiration(expirationDate)
			.signWith(refreshPrivateKey)
			.compact();

		redisCachingService.setValuesWithDuration(userId, refreshToken, Duration.ofMillis(refreshExpirationMillis));

		return refreshToken;
	}

	@Override
	public boolean isValidRefreshToken(String refreshToken) {
		if (StringUtils.hasText(refreshToken)) {
			try {
				Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(refreshPrivateKey).build()
					.parseClaimsJws(refreshToken);

				return !claims.getBody().getExpiration().before(new Date());
			} catch (ExpiredJwtException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean isValidAccessToken(String accessToken) {
		if (StringUtils.hasText(accessToken)) {
			try {
				Jws<Claims> claims = Jwts.parserBuilder()
					.setSigningKey(accessPrivateKey)
					.build()
					.parseClaimsJws(accessToken);

				boolean valid = claims.getBody()
					.getExpiration()
					.after(new Date());

				return valid;
			} catch (ExpiredJwtException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public JwtToken getTokenFromHeader(HttpServletRequest request) {
		JwtToken token =  JwtToken.builder()
			.accessToken(request.getHeader("authorization"))
			.refreshToken(request.getHeader("RefreshToken"))
			.build();

		return token;
	}

	@Override
	public String getUserIdFromToken(String accessToken) {
		String userId =  (String) Jwts.parserBuilder()
			.setSigningKey(accessPrivateKey)
			.build()
			.parseClaimsJws(accessToken)
			.getBody()
			.get("userId");

		return userId;
	}

	@Override
	public Collection<GrantedAuthority> getRolesFromToken(String accessToken) {
		List<String> roles =  (List) Jwts.parserBuilder()
			.setSigningKey(accessPrivateKey)
			.build()
			.parseClaimsJws(accessToken)
			.getBody()
			.get("roles");

		List<GrantedAuthority> roleList = roles.stream()
			.map(role -> new SimpleGrantedAuthority(role))
			.collect(Collectors.toList());

		return roleList;
	}
}
