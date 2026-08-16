package com.example.secureservice.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestLoggingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		try {
			filterChain.doFilter(request, response);
		} finally {
			long duration = System.currentTimeMillis() - startTime;
			int status = response.getStatus();
			String message = String.format("HTTP %s %s -> %d (%d ms)", request.getMethod(), request.getRequestURI(),
					status, duration);

			if (status >= 500) {
				log.error(message);
			} else if (status >= 400) {
				log.warn(message);
			} else {
				log.info(message);
			}
		}
	}
}
