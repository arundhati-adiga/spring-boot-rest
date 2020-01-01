package com.apiapp.exception;

/**
 * Custom exception which is thrown when create category operation fails
 * @author Arundhati Adiga
 *CreateCategoryException.java
 */
public class CreateCategoryException  extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

		
		public CreateCategoryException(String categoryId, String message) {
			  super("Creation of category failed for " + categoryId+ message);
		}


		public CreateCategoryException(String categoryId) {
			super("Creation of category failed for " + categoryId +" .Category Already Exists");
		}

}
