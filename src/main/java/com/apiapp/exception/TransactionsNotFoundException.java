package com.apiapp.exception;

import java.util.Arrays;

public class TransactionsNotFoundException  extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

		public TransactionsNotFoundException(String[] categoryIds) {
	        super("Transactions not found for given categories" + Arrays.toString(categoryIds));
	    }

		public TransactionsNotFoundException(String categoryId) {
			  super("Transactions not found for given categories" + categoryId);
		}

}
