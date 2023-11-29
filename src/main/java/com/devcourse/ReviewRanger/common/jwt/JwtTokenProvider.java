package com.devcourse.ReviewRanger.common.jwt;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.auth.application.CustomUserDetailsService;
import com.devcourse.ReviewRanger.auth.domain.RefreshToken;
import com.devcourse.ReviewRanger.auth.repository.RefreshTokenRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.common.redis.RedisUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
	private static final long ACCESS_EXPIRATION_TIME = 30 * 60 * 1000L; // 30 min
	private static final long REFRESH_EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24 hour
	private static final String AUTHORITY = "auth";

	private final Key key;
	private final CustomUserDetailsService userDetailService;
	private final RedisUtil redisUtil;
	private final RefreshTokenRepository refreshTokenRepository;

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, CustomUserDetailsService userDetailService,
		RedisUtil redisUtil, RefreshTokenRepository refreshTokenRepository) {
		this.userDetailService = userDetailService;
		this.redisUtil = redisUtil;
		this.refreshTokenRepository = refreshTokenRepository;
		byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
		this.key = Keys.hmacShaKeyFor(secretByteKey);
	}

	public String createAccessToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITY, authorities)
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITY, authorities)
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			log.warn("JWT Exception Occurs : {}", LOGOUT_JWT_TOKEN);
			throw new RangerException(NOT_AUTHORIZED_TOKEN);
		}

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();

		String username = claims.getSubject();
		UserDetails user = userDetailService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(user, "", authorities);
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

			if (redisUtil.hasKeyBlackList(token)) {
				log.warn("JWT Exception Occurs : {}", LOGOUT_JWT_TOKEN);
				throw new JwtException(LOGOUT_JWT_TOKEN.getMessage());
			}

			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.warn("JWT Exception Occurs : {}", NOT_CORRECT_JWT_SIGN);
			throw new JwtException(NOT_CORRECT_JWT_SIGN.getMessage());
		} catch (ExpiredJwtException e) {
			log.warn("JWT Exception Occurs : {}", EXPIRED_JWT_TOKEN);
			throw new ExpiredJwtException(null, null, EXPIRED_JWT_TOKEN.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("JWT Exception Occurs : {}", NOT_SUPPORTED_JWT_TOKEN);
			throw new JwtException(NOT_SUPPORTED_JWT_TOKEN.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("JWT Exception Occurs : {}", NOT_CORRECT_JWT);
			// throw new JwtException(NOT_SUPPORTED_JWT_TOKEN.getMessage()); TODO: Postman에서 로그인할 때 오류남 따라서 임시 조치
		}
		return false;
	}

	public Boolean validateRefreshToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) { // 기한 만료
			throw new ExpiredJwtException(null, null, EXPIRED_JWT_TOKEN.getMessage());
		}
	}

	public String getRefreshToken(Long userId) {
		RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
			.orElseThrow(() -> new RangerException(INVALID_REFRESH_TOKEN));
		return refreshToken.getRefreshToken();
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
