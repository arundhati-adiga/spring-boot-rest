package com.apiapp.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

/**
 * Wrapper class for List of Categorized Transaction which is used in getCategorized Transactions
 * @author Arundhati Adiga
 * 
 */
public class CategorizedTransactionsList  extends RepresentationModel{

	
	public CategorizedTransactionsList() {
		
	}
	
	
	public CategorizedTransactionsList(List<CategorisedTransaction> categorizedTransactions) {
		super();
		this.categorizedTransactions = categorizedTransactions;
	}

	private List<CategorisedTransaction> categorizedTransactions;

	public List<CategorisedTransaction> getCategorizedTransactions() {
		return categorizedTransactions;
	}

	

	public void setCategorizedTransactions(List<CategorisedTransaction> categorizedTransactions) {
		this.categorizedTransactions = categorizedTransactions;
	}

	@Override
	public String toString() {
		return "CategorizedTransactionsList [categorizedTransactions=" + categorizedTransactions + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((categorizedTransactions == null) ? 0 : categorizedTransactions.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategorizedTransactionsList other = (CategorizedTransactionsList) obj;
		if (categorizedTransactions == null) {
			if (other.categorizedTransactions != null)
				return false;
		} else if (!categorizedTransactions.equals(other.categorizedTransactions))
			return false;
		return true;
	}

	
	
	
	
	
}
