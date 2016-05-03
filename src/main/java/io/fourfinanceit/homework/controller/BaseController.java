package io.fourfinanceit.homework.controller;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.fourfinanceit.homework.exception.DefaultExceptionAttributes;
import io.fourfinanceit.homework.exception.ExceptionAttributes;

@ControllerAdvice
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Handles JPA NoResultExceptions thrown from web service controller
     * methods. Creates a response with an empty body and HTTP status code 404,
     * not found.
     * 
     * @param nre A NoResultException instance.
     * @return A ResponseEntity with an empty response body and HTTP status code
     *         404.
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String,Object>> handleNoResultException(
            NoResultException nre,HttpServletRequest request) {
        logger.error("> handleNoResultException");
        logger.error("- NoResultException: ", nre);
        logger.error("< handleNoResultException");
        
        ExceptionAttributes exceptionAttribute = new DefaultExceptionAttributes();
        
        Map<String,Object> responseBody = exceptionAttribute.getExceptionAttributes(nre,request, HttpStatus.INTERNAL_SERVER_ERROR);
        
        return new ResponseEntity<Map<String,Object>>(responseBody,HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods. Creates a response with the
     * Exception detail in the response body as JSON and a HTTP status code of
     * 500, internal server error.
     * 
     * @param e An Exception instance.
     * @return A ResponseEntity containing a the Exception attributes in the
     *         response body and a HTTP status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception e,HttpServletRequest request) {
        logger.error("> handleException");
        logger.error("- Exception: ", e);
        logger.error("< handleException");
        ExceptionAttributes exceptionAttribute = new DefaultExceptionAttributes();
        
        Map<String,Object> responseBody = exceptionAttribute.getExceptionAttributes(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
        
        return new ResponseEntity<Map<String,Object>>(responseBody,HttpStatus.INTERNAL_SERVER_ERROR);
                
    }

}