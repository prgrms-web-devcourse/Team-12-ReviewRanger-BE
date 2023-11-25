package com.devcourse.ReviewRanger.common.jwt;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devcourse.ReviewRanger.common.exception.RangerException;

import io.jsonwebtoken.JwtException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException ex) {
			throw new RangerException(NOT_CORRECT_JWT);
		}
	}
}
