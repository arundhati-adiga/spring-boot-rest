package com.apiapp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.Transaction;

/**
 * This class is responsible for caching the data got the fractal API
 * 
 * @author Arundhati Adiga InmemoryDataStore.java
 */
public class InmemoryDataStore {

	private static List<Transaction> bankTransaction = new ArrayList<>();

	private static Map<String, CategorisedTransaction> categoryList = new HashMap<>();

	public static Map<String, CategorisedTransaction> getCategoryList() {

		/**if (categoryList.isEmpty() && (!bankTransaction.isEmpty())) {

			categoryList = bankTransaction.stream()
					.collect(Collectors.toMap(Transaction::getDescription,
							transaction -> new CategorisedTransaction(transaction.getDescription(),
									transaction.getDescription())));

		}**/

		return categoryList;
	}

	public static void setCategoryList(Map<String, CategorisedTransaction> categoryList) {
		InmemoryDataStore.categoryList = categoryList;
	}

	public static List<Transaction> getBankTransaction() {
		return bankTransaction;
	}

	public static void setBankTransaction(List<Transaction> bankTransaction) {
		InmemoryDataStore.bankTransaction = bankTransaction;
	}

}
