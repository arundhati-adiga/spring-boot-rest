package com.apiapp.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Arrays;

import org.springframework.hateoas.Link;

import com.apiapp.controller.CategoryController;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.CategorizedTransactionsList;

/**
 * Add the links 
 * @author Arundhati Adiga
 *ResponseAssembler.java
 */

public class ResponseAssembler {
	
	/**
	 * Add link page info for get service
	 * @param categorizedTransaction
	 * @param page
	 * @param size
	 * @param categoryIds
	 * @return
	 */
	public CategorizedTransactionsList addLinks(CategorizedTransactionsList categorizedTransaction,int page,int size, String[] categoryIds) {
		
		Link self=linkTo(CategoryController.class).slash(Arrays.toString(categoryIds)).withSelfRel();
		Link next=linkTo(CategoryController.class).slash(Arrays.toString(categoryIds)).slash(page+1).slash(size).withRel("Next");
		Link first=linkTo(CategoryController.class).slash(Arrays.toString(categoryIds)).slash(1).slash(size).withRel("First");

		categorizedTransaction.add(self);
		categorizedTransaction.add(next);
		categorizedTransaction.add(first);
		
		return categorizedTransaction;
	}

	public CategorisedTransaction addLinks(CategorisedTransaction newCategorizedtransaction) {
		Link self=linkTo(CategoryController.class).slash(newCategorizedtransaction.getCategoryId()).withSelfRel();
		newCategorizedtransaction.add(self);
		return newCategorizedtransaction;
	}
	 
	
}
