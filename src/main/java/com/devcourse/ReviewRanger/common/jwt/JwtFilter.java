package com.devcourse.ReviewRanger.common.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtTokenProvider jwtTokenProvider;

	public JwtFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = resolveToken(request, "Authorization");

		try {
			if (StringUtils.hasText(accessToken) && jwtTokenProvider.isValidToken(accessToken)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (ExpiredJwtException e) {
			String refreshToken = null;

			if (StringUtils.hasText(request.getHeader("Authorization"))) {
				Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
				UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
				Long userId = principal.getId();
				refreshToken = jwtTokenProvider.getRefreshToken(userId);
			}

			if (StringUtils.hasText(refreshToken) && jwtTokenProvider.validateRefreshToken(refreshToken)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
				String newAccessToken = jwtTokenProvider.createAccessToken(authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				response.setHeader(HttpHeaders.AUTHORIZATION, newAccessToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request, String header) {
		String bearerToken = request.getHeader(header);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
