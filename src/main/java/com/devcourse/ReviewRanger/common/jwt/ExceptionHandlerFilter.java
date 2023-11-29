package com.devcourse.ReviewRanger.common.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	record ErrorResponse(
		String messsage
	) {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException ex) {
			setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ex);
		}
	}

	public void setErrorResponse(HttpStatus status, HttpServletRequest request,
		HttpServletResponse response, Throwable ex) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		response.setStatus(status.value());
		response.setContentType("application/json; charset=UTF-8");

		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
