package com.apiapp.transactioncategoryservice;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.apiapp.model.BankTransaction;
import com.apiapp.model.BankTransactionSearchInfo;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.CategorizedTransactionsList;
import com.apiapp.model.CategorySearchInfo;
import com.apiapp.model.PageInfo;
import com.apiapp.model.Transaction;
import com.apiapp.service.CategoryService;
import com.apiapp.service.TransactionService;

/**
 * Test Data is available at /test/testdata.txt
 * @author Arundhati Adiga
 *CategoryServiceTest.java
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryServiceTest {

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private CategoryService categoryService;

	CategorizedTransactionsList categorizedTransactionsList;

	
	
	
	@Test
	public void testGetCategoriesSuccess() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		String[] categoryIds = { "Starbucks Victoria Stn" };

		CategorySearchInfo categorySearchInfo = new CategorySearchInfo();
		categorySearchInfo.setCategoryIds(Arrays.asList(categoryIds));
		categorySearchInfo.setPageInfo(new PageInfo(1, 1));

		CategorizedTransactionsList result = categoryService.getCategorizedTransactions(categorySearchInfo);

		// Verify request succeed
		assertEquals(1, result.getCategorizedTransactions().size());
		assertEquals(true,
				result.getCategorizedTransactions().get(0).getCategoryId().equalsIgnoreCase("Starbucks Victoria Stn"));

	}

	@Test
	public void testGetCategoriesFailureNotFound() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		String[] categoryIds = { "not found" };

		CategorySearchInfo categorySearchInfo = new CategorySearchInfo();
		categorySearchInfo.setCategoryIds(Arrays.asList(categoryIds));
		categorySearchInfo.setPageInfo(new PageInfo(1, 1));

		CategorizedTransactionsList result = categoryService.getCategorizedTransactions(categorySearchInfo);

		// Verify request succeed
		assertEquals(0, result.getCategorizedTransactions().size());

	}

	@Test
	public void testGetCategoriesSucessMultipleCategories() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		String[] categoryIds = { "Starbucks Victoria Stn", "SAINSBURY'S" };

		CategorySearchInfo categorySearchInfo = new CategorySearchInfo();
		categorySearchInfo.setCategoryIds(Arrays.asList(categoryIds));
		categorySearchInfo.setPageInfo(new PageInfo(1, 6));

		CategorizedTransactionsList result = categoryService.getCategorizedTransactions(categorySearchInfo);

		// Verify request succeed
		assertEquals(2, result.getCategorizedTransactions().size());
	}

	@Test
	public void testGetCategoriesSucessIgnoreCase() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		String[] categoryIds = { "starbucks Victoria Stn", "sainsbury'S" };

		CategorySearchInfo categorySearchInfo = new CategorySearchInfo();
		categorySearchInfo.setCategoryIds(Arrays.asList(categoryIds));
		categorySearchInfo.setPageInfo(new PageInfo(1, 6));

		CategorizedTransactionsList result = categoryService.getCategorizedTransactions(categorySearchInfo);

		// Verify request succeed
		assertEquals(2, result.getCategorizedTransactions().size());
	}

	@Test
	public void testGetCategoriesSucessMultipleTransactions() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		String[] categoryIds = { "BT MOBILE" };

		CategorySearchInfo categorySearchInfo = new CategorySearchInfo();
		categorySearchInfo.setCategoryIds(Arrays.asList(categoryIds));
		categorySearchInfo.setPageInfo(new PageInfo(1, 6));

		CategorizedTransactionsList result = categoryService.getCategorizedTransactions(categorySearchInfo);

		// Verify request succeed
		assertEquals(true,
				result.getCategorizedTransactions().get(0).getCategoryId().equalsIgnoreCase("BT MOBILE"));
		assertEquals(4, result.getCategorizedTransactions().get(0).getBankTransaction().size());
	}

	@Test
	public void testupdateCategoriesForAllTransactions() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("updated CashPoint Angel",
				" updated CashPoint Angel Desc");

		BankTransaction result = categoryService.updateCategory(categorisedTransaction, "CashPoint Angel",new BankTransactionSearchInfo());

		// Verify request succeed
		// assertEquals(true,
		// result.get(0).getCategoryId().equalsIgnoreCase("updated CashPoint Angel" ));
		// there are 2 transactions with desc "CashPoint Angel"
		assertEquals(2, result.getResults().size());
	}

	@Test
	public void testupdateCategoriesForgivenTransactions() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		List<Transaction> bankTransactions = new ArrayList<>();

		Transaction transaction1 = new Transaction("c98c9faa-c000-4efe-a5a4-9f09b65aa1ec");
		bankTransactions.add(transaction1);
		Transaction transaction2 = new Transaction("5875d68d-d53c-4f4b-a447-1606059fe57f");
		bankTransactions.add(transaction2);
		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category BT MOBILE",
				"new category Desc BT MOBILE");
		categorisedTransaction.setBankTransaction(bankTransactions);

		BankTransaction result = categoryService.updateCategory(categorisedTransaction, "BT MOBILE",new BankTransactionSearchInfo());

		// Verify request succeed
		assertEquals(2, result.getResults().size());

	}

	@Test
	public void testupdateCategoriesFailureNotValidCategory() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category BT MOBILE",
				"new category Desc BT MOBILE");

		BankTransaction result = categoryService.updateCategory(categorisedTransaction, "Not Valid Category",new BankTransactionSearchInfo());

		// Verify request succeed
		assertEquals(0, result.getResults().size());

	}

	@Test
	public void testupdateCategoriesFailureNotValidCategoryWithTransactions() {
		// RestTemplate restTemplate = new RestTemplate();

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		List<Transaction> bankTransactions = new ArrayList<>();

		Transaction transaction1 = new Transaction("c98c9faa-c000-4efe-a5a4-9f09b65aa1ec");
		bankTransactions.add(transaction1);
		Transaction transaction2 = new Transaction("5875d68d-d53c-4f4b-a447-1606059fe57f");
		bankTransactions.add(transaction2);
		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category BT MOBILE",
				"new category Desc BT MOBILE");
		categorisedTransaction.setBankTransaction(bankTransactions);

		BankTransaction result = categoryService.updateCategory(categorisedTransaction, "Not Valid Category",new BankTransactionSearchInfo());

		// Verify request succeed
		assertEquals(0, result.getResults().size());

	}

	@Test
	public void testupdateCategoriesFailureNotValidCategoryforGivenTransactions() {
		

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		List<Transaction> bankTransactions = new ArrayList<>();

		Transaction transaction1 = new Transaction("c98c9faa-c000-4efe-a5a4-9f09b65aa1ec");
		bankTransactions.add(transaction1);
		Transaction transaction2 = new Transaction("5875d68d-d53c-4f4b-a447-1606059fe57f");
		bankTransactions.add(transaction2);
		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category BT MOBILE",
				"new category Desc BT MOBILE");
		categorisedTransaction.setBankTransaction(bankTransactions);

		BankTransaction result = categoryService.updateCategory(categorisedTransaction, "CashPoint Angel",new BankTransactionSearchInfo());

		// Verify request succeed
		assertEquals(0, result.getResults().size());

	}

	
	
	@Test
	public void testupdateCategoriescreateSuccess() {
		

		Mockito.when(transactionService.getcompanybankTransactions(Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getMockBankTransactions());

		
		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category BT MOBILE",
				"new category Desc BT MOBILE");
		

		CategorisedTransaction result = categoryService.createCategory(categorisedTransaction);

		// Verify request succeed
		assertEquals(true, result.getCategoryId().equals("new category BT MOBILE"));

	}
	
	
	@Test
	public void testupdateCategoriescreateFailureForDuplicate() {
		
      Map<String,CategorisedTransaction> categoryMap=new HashMap<>();
      CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category BT MOBILE",
				"new category Desc BT MOBILE");
      categoryMap.put("new category BT MOBILE",categorisedTransaction);
      
		Mockito.when(transactionService.getExistngCategories())
				.thenReturn(categoryMap);

		CategorisedTransaction result = categoryService.createCategory(categorisedTransaction);

		// Verify request succeed
		assertEquals(null, result);

	}
	

	
	
}