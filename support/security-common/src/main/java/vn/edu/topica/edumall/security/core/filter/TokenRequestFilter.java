package vn.edu.topica.edumall.security.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenRequestFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		log.info("(doFilter)START");
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
				if (StringUtils.isBlank(requestTokenHeader)) {
					requestTokenHeader = getCookieToken(request, HttpHeaders.AUTHORIZATION);
				}
				authentication = tokenProvider.getAuthentication(requestTokenHeader);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				log.info("(doFilter) FINISH not authenticated");
			} else {
				log.info("(doFilter)FINISH authenticated");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}
		chain.doFilter(request, response);
	}

	private String getCookieToken(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
