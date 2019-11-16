package vn.edu.topica.edumall.api.rest.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.api.rest.ApiResponse;
import vn.edu.topica.edumall.api.rest.exception.ErrorBuilder;

@RestController
@Slf4j
public class ErrorController extends AbstractErrorController {
	private ErrorAttributes errorAttributes;
	private final ServerProperties serverProperties;
	@Value("${error.path:/error}")
	private String errorPath;
	ErrorBuilder errorBuilder;

	@Autowired
	public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
			ErrorBuilder errorBuilder) {
		super(errorAttributes);
		this.errorAttributes = errorAttributes;
		this.serverProperties = serverProperties;
		this.errorBuilder = errorBuilder;
	}

	@Override
	public String getErrorPath() {
		return errorPath;
	}

	@RequestMapping(value = "${error.path:/error}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity error(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		WebRequest webRequest = new ServletWebRequest(request);
		Throwable throwable = errorAttributes.getError(webRequest);
		Map<String, Object> errors = errorBuilder.putErrorTrace(status, throwable);
		errorBuilder.putErrorMessage(errors, throwable);
		errorBuilder.putDevMsg(errors, webRequest, status, throwable);
		return ResponseEntity.status(status).body(ApiResponse.error().setErrors(errors));
	}
}
