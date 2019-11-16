package vn.edu.topica.edumall.api.rest.exception;

public class ApiCodeException extends RuntimeException implements CodeException {

	private static final long serialVersionUID = 5327660866495219790L;

	public ApiCodeException() {
	}

	public ApiCodeException(String message) {
		super(message);
	}

	public ApiCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiCodeException(Throwable cause) {
		super(cause);
	}

	public ApiCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	Integer exceptionCode;

	public ApiCodeException setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
		return this;
	}

	@Override
	public Integer getUserFriendlyExceptionCode() {
		return exceptionCode;
	}

	@Override
	public String getUserFriendlyMessage() {
		return null;
	}
}
