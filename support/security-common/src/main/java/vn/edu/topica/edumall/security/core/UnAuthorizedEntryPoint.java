package vn.edu.topica.edumall.security.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.security.config.SecurityConstant;

@Component
@Slf4j
public class UnAuthorizedEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Responding with unauthorized error. Message - {}", request.getAttribute("Authorization"));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 : " + authException.getMessage());

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName(SecurityConstant.SECURITY_REAML);
		super.afterPropertiesSet();
	}
}
