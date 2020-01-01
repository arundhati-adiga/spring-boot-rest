package com.apiapp.exception;

/**
 * Custom exception which is thrown when there is an issue accessing Fractal service
 * @author Arundhati Adiga
 *FractalServiceException.java
 */
public class FractalServiceException  extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

		public FractalServiceException(String errorcode) {
	        super("There was an error while invoking Fractal API. With error code" + errorcode);
	    }

	
}
