package com.apiapp.exception;

import java.util.Arrays;
/**
 * Custom exception which is thrown when transactions are not found for the given category
 * @author Arundhati Adiga
 *CategoryNotFoundException.java
 */
public class CategoryNotFoundException  extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

		public CategoryNotFoundException(String[] categoryIds) {
	        super("Invalid category or transactions were not found for given categories  " + Arrays.toString(categoryIds));
	    }

		public CategoryNotFoundException(String categoryId) {
			  super("Invalid category or transactions were not found for given categories  " + categoryId);
		}

}
