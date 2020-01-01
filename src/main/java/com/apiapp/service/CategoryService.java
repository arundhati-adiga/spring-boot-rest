package com.apiapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.apiapp.dao.InmemoryDataStore;
import com.apiapp.model.BankTransaction;
import com.apiapp.model.BankTransactionSearchInfo;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.CategorizedTransactionsList;
import com.apiapp.model.CategorySearchInfo;
import com.apiapp.model.Transaction;

/**
 * This class is responsible for FetchingTransactions based on category,
 * updating category and creating.
 * 
 * @author Arundhati Adiga CategoryService.java
 */
@Service
@Validated
public class CategoryService {

	@Autowired
	private TransactionService transactionService;

	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	/**
	 * This method is responsible for returning the list of transactions based on
	 * the categoryyId(transaction description) Use Fractal API Sandbox to access a
	 * company’s bank transaction.
	 * 
	 * @param categorySerachInfo
	 * @return CategorizedTransactionsList
	 */
	public CategorizedTransactionsList getCategorizedTransactions(@NotNull @Valid CategorySearchInfo categorySerachInfo) {

		BankTransactionSearchInfo bankTransactionSearchInfo =new BankTransactionSearchInfo();
		bankTransactionSearchInfo.setAccountId(categorySerachInfo.getAccountId());
		bankTransactionSearchInfo.setBankId(categorySerachInfo.getBankId());
		
		bankTransactionSearchInfo.setCompanyId(categorySerachInfo.getCompanyId());
		
		
		List<Transaction> bankTransactions = transactionService
				.getcompanybankTransactions(bankTransactionSearchInfo);

		logger.debug("Got the banktransaction from Fractal API  {}", bankTransactions);

		List<Transaction> categorizedTransactionsList = mapTransactionsCategory(categorySerachInfo, bankTransactions);

		return populateResultEntity(categorizedTransactionsList);

	}

	/**
	 * This method is used to populate data in the ResultEntity
	 * CategorizedTransactionsList
	 * 
	 * @param categorizedTransactionsList
	 * @return CategorizedTransactionsList
	 */
	private CategorizedTransactionsList populateResultEntity(List<Transaction> categorizedTransactionsList) {

		List<CategorisedTransaction> result;

		Map<String, List<Transaction>> categorizedMap;

		// Populate a map by grouping the list based on description
		categorizedMap = categorizedTransactionsList.stream()
				.collect(Collectors.groupingBy(Transaction::getDescription));

		// Create the list of CategorisedTransaction(categoryid, List<BankTransactions>
		// using the map
		result = categorizedMap.entrySet().stream().map(m -> new CategorisedTransaction(m.getKey(), m.getValue()))
				.collect(Collectors.toList());

		return new CategorizedTransactionsList(result);
	}

	/**
	 * Categorize the transactions based on category . Using JAVA8 parallel stream
	 * feature to categorize the transactions
	 * 
	 * @param categorySerachInfo
	 * @param bankTransactions
	 * @return List<Transaction>
	 */
	private List<Transaction> mapTransactionsCategory(CategorySearchInfo categorySerachInfo,
			List<Transaction> bankTransactions) {

		List<Transaction> categorizedBankTransactions = new ArrayList<>();
		
		//Converting the search category string to lower case to handle ignore case .
		if(categorySerachInfo.getCategoryIds()!=null) {
		categorySerachInfo.getCategoryIds().replaceAll(String::toLowerCase);

		// Filter the list based on description and then collect the result in a new
		// list. Used Parallel stream 
		if (bankTransactions != null && (!bankTransactions.isEmpty())) {
			categorizedBankTransactions = bankTransactions.parallelStream()
					.filter(i -> categorySerachInfo.getCategoryIds().contains(i.getDescription().toLowerCase()))
					.collect(Collectors.toList());

		}

		// once we have the categorized transactions , return the list based on
		// pagination
		categorizedBankTransactions = getTransactionsBasedonPagination(categorizedBankTransactions,
				categorySerachInfo.getPageInfo().getPage(), categorySerachInfo.getPageInfo().getSize());
		}
		return categorizedBankTransactions;

	}

	/**
	 * Sublist the categorized transactions based on page num and page size
	 * 
	 * @param categorizedBankTransactions
	 * @param page
	 * @param size
	 * @return List<Transaction>
	 */
	private List<Transaction> getTransactionsBasedonPagination(List<Transaction> categorizedBankTransactions, int page,
			int size) {

		List<Transaction> paginatedTransactions = new ArrayList<>();

		if (!categorizedBankTransactions.isEmpty() && page > 0 && size > 0) {

			int fromIndex = page * size - size;
			int lastIndex = page * size;

			if (fromIndex > categorizedBankTransactions.size()) {

				return paginatedTransactions;
			}

			if (lastIndex > categorizedBankTransactions.size()) {

				lastIndex = categorizedBankTransactions.size();
			}

			return categorizedBankTransactions.subList(fromIndex, lastIndex);

		}
		return paginatedTransactions;
	}

	/**
	 * Method to update the category of the transactions. As we are not using any DB
	 * , transactions are stored in static Arraylist . we update the
	 * @param bankTransactionSearchInfo 
	 * 
	 * @param transactions
	 * @param categoryId
	 * @return
	 */
	public BankTransaction updateCategory(@NotNull @Valid CategorisedTransaction categorisedTransaction,
			@NotBlank String oldCategoryId, BankTransactionSearchInfo bankTransactionSearchInfo) {

		BankTransaction updatedBankTransactions = new BankTransaction();

		List<Transaction> updatedTransactionList ;

		List<Transaction> bankTransactions = transactionService
				.getcompanybankTransactions(bankTransactionSearchInfo);

		//Preparing a map with transactionId as key so that comparison becomes easy 
		Map<String, Transaction> inputTransactionMap = getTransactionMap(categorisedTransaction.getBankTransaction());
		
		//Compare the description with inputCategory and update with new description .
		updatedTransactionList = bankTransactions.stream()
				.filter(i -> ((inputTransactionMap != null && inputTransactionMap.containsKey(i.getTransactionId()) && oldCategoryId.equalsIgnoreCase(i.getDescription())  )
						|| (inputTransactionMap.isEmpty() && (oldCategoryId.equalsIgnoreCase(i.getDescription())))))
				.map(i -> {
					i.setDescription(categorisedTransaction.getCategoryId());
					i.setVersion(i.getVersion() + 1);
					return i;
				}).collect(Collectors.toList());

		updatedBankTransactions.setResults(updatedTransactionList);
		//updated the cache with updated transactions
		InmemoryDataStore.setBankTransaction(bankTransactions);
		logger.debug("updated list of transactions {}", updatedTransactionList);
		return updatedBankTransactions;
	}

	private Map<String, Transaction> getTransactionMap(List<Transaction> bankTransaction) {

		Map<String, Transaction> inputTransactionMap = new HashMap<>();
		if (bankTransaction != null && (!bankTransaction.isEmpty())) {
			inputTransactionMap = bankTransaction.stream()
					.collect(Collectors.toMap(Transaction::getTransactionId, transaction -> transaction));
		}

		return inputTransactionMap;

	}


	

	/**
	 * Add Category
	 * @param categorizedtransaction
	 * @return
	 */
	public CategorisedTransaction createCategory(@NotNull @Valid CategorisedTransaction categorizedtransaction) {

		Map<String, CategorisedTransaction> categoryList=transactionService.getExistngCategories();
		
		
		if (!categoryList.containsKey(categorizedtransaction.getCategoryId().trim())) {

			CategorisedTransaction newCategory=new CategorisedTransaction(categorizedtransaction.getCategoryId().trim(),categorizedtransaction.getCategoryDesc());
			categoryList.put(categorizedtransaction.getCategoryId().trim(), newCategory);
			InmemoryDataStore.setCategoryList(categoryList);
			return categorizedtransaction;

		} else {

			return null;
		}

	}

	
	
	
	
	
}
