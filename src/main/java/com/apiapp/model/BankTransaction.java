package com.apiapp.model;

import java.util.ArrayList;
import java.util.List;



/**
 * Resource for getting results from Fractal API
 * @author Arundhati Adiga
 *BankTransaction.java
 */
public class BankTransaction {

	public BankTransaction() {
		
	}
	
	
	private List<Transaction> results =new ArrayList<>();
	
	
	private Links links;
	

	public List<Transaction> getResults() {
		return results;
	}

	public void setResults(List<Transaction> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "BankTransaction [results=" + results + ", links=" + links + "]";
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	


	
	
	
}
