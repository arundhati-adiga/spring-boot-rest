package com.apiapp.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apiapp.exception.CategoryNotFoundException;
import com.apiapp.exception.CreateCategoryException;
import com.apiapp.exception.TransactionsNotFoundException;

/**This class is responsible for exception handling at global scope .
 *We use @ControllerAdvice to handle exception with globle scope of application, not only in an individial controller.
 * @author Arundhati Adiga
 *GlobalExceptionHandler.java
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	
	   @ExceptionHandler(ConstraintViolationException.class)
	    public void constraintViolationException(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.BAD_REQUEST.value());
	    }
	

	   @ExceptionHandler(BadCredentialsException.class)
	    public void badCredentialsException(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.UNAUTHORIZED.value());
	    }
	   

	   @ExceptionHandler(ValidationException.class)
	    public void validationException(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.BAD_REQUEST.value());
	    }
	   
	 
	   @ExceptionHandler(TransactionsNotFoundException.class)
	    public void transactionsNotFoundException(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.NOT_FOUND.value());
	    }
	   
	   @ExceptionHandler(CreateCategoryException.class)
	    public void createCategoryException(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.CONFLICT.value());
	    }
	   
	   @ExceptionHandler( CategoryNotFoundException.class)
	    public void  categoryNotFoundException(HttpServletResponse response) throws IOException {
	        response.sendError(HttpStatus.NOT_FOUND.value());
	    }
	   
	  
	  
	   
	   
}
