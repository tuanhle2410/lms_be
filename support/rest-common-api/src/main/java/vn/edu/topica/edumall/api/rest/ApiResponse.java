package vn.edu.topica.edumall.api.rest;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class ApiResponse<T> {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";

	@JsonProperty
	String status;
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	T data;
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	Map<String, Object> errors;

	private ApiResponse() {
	}

	public ApiResponse(T data) {
		this.data = data;
	}

	public ApiResponse<T> setStatus(String status) {
		this.status = status;
		return this;
	}

	public ApiResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

	public Map<String, Object> getErrors() {
		return errors;
	}

	public ApiResponse<T> setErrors(Map<String, Object> errors) {
		this.errors = errors;
		return this;
	}

	public ApiResponse<T> addError(String key, Object value) {
		if (errors == null) {
			errors = Maps.newHashMap();
		}
		this.errors.put(key, value);
		return this;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(data).setStatus(SUCCESS);
	}

	public static <T> ApiResponse<T> fail(T data) {
		return new ApiResponse<>(data).setStatus(FAIL);
	}

	public static <T> ApiResponse<T> error() {
		return new ApiResponse<T>().setStatus(ERROR);
	}

	public static <T> ApiResponse<T> create(Class<T> clazz) {
		return new ApiResponse<>();
	}

	public static <T> ApiResponse<T> create() {
		return new ApiResponse<>();
	}
}
