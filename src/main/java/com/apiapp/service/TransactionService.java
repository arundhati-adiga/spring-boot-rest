package com.apiapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiapp.dao.InmemoryDataStore;
import com.apiapp.model.BankTransactionSearchInfo;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.Transaction;

/**
 * This class is responsible for fectching the list of bank transactions from fractal API 
 * @author Arundhati Adiga
 *TransactionService.java
 */
@Service
public class TransactionService {

	@Autowired
	private FractalAPIServiceExecutor fractalApiExecutor;
	
	
	

	public FractalAPIServiceExecutor getFractalApiExecutor() {
		return fractalApiExecutor;
	}




	public void setFractalApiExecutor(FractalAPIServiceExecutor fractalApiExecutor) {
		this.fractalApiExecutor = fractalApiExecutor;
	}




	/**
	 * Gets the list of bank transactions for a company using Fractal API . If data
	 * already exists in memory , then service is not called .
	 * 
	 * @param bankTransactionSerachInfo
	 * @return
	 */
	public List<Transaction> getcompanybankTransactions(BankTransactionSearchInfo bankTransactionSerachInfo) {
		
			List<Transaction> bankTransaction = fractalApiExecutor
					.getBankTransactionsForCompany(bankTransactionSerachInfo);

			InmemoryDataStore.setBankTransaction(bankTransaction);

		
		return InmemoryDataStore.getBankTransaction();
	}
	
	
	
	/**
	 * Get the list of existing categories from InMemory datastore
	 * @return
	 */
	public Map<String, CategorisedTransaction> getExistngCategories() {
		return InmemoryDataStore.getCategoryList();
		
	}
	
	
}
