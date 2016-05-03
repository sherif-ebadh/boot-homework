package io.fourfinanceit.homework.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class DefaultExceptionAttributes implements ExceptionAttributes {

	public static final String TIMESTAMP = "timestamp";
	public static final String STATUS = "status";
	public static final String ERROR = "error";
	public static final String EXCEPTION = "exception";
	public static final String MESSAGE = "message";
	public static final String PATH = "path";

	public Map<String, Object> getExceptionAttributes(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
		Map<String, Object> exceptionAttributes = new HashMap<String, Object>();
		exceptionAttributes.put(TIMESTAMP, new Date());
		addHttpStatus(exceptionAttributes, httpStatus);
		addPath(exceptionAttributes, request);
		addExceptionDetails(exceptionAttributes, ex);
		return exceptionAttributes;
	}

	private void addHttpStatus(Map<String, Object> exceptionAttributes, HttpStatus httpStatus) {
		exceptionAttributes.put(STATUS, httpStatus.value());
		exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());
	}

	private void addExceptionDetails(Map<String, Object> exceptionAttributes, Exception exception) {
		exceptionAttributes.put(MESSAGE, exception);
		exceptionAttributes.put(EXCEPTION, exception.getClass().getName());
	}

	private void addPath(Map<String, Object> exceptionAttributes, HttpServletRequest httpRequest) {
		exceptionAttributes.put(PATH, httpRequest);
	}
}
