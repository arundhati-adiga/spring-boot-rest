package com.apiapp.controller;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiapp.exception.CategoryNotFoundException;
import com.apiapp.exception.CreateCategoryException;
import com.apiapp.exception.TransactionsNotFoundException;
import com.apiapp.model.BankTransaction;
import com.apiapp.model.BankTransactionSearchInfo;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.CategorizedTransactionsList;
import com.apiapp.model.CategorySearchInfo;
import com.apiapp.model.PageInfo;
import com.apiapp.service.CategoryService;
import com.apiapp.service.ResponseAssembler;
import com.apiapp.validator.NotEmptyFields;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Rest Controller for Category API
 * @author Arundhati Adiga
 *CategoryController.java
 */

@RestController
@Validated
@RequestMapping("/categories")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	ResponseAssembler responseAssembler = new ResponseAssembler();

	private Logger logger = LoggerFactory.getLogger(CategoryController.class);

	private static final String DEFAULT_PAGE_SIZE = "30";
	private static final String DEFAULT_PAGE = "1";

	/**
	 * Get the bank transactions for the given categories 
	 * @param categoryIds
	 * @param page
	 * @param size
	 * @return
	 * 
	 */
	@Operation(summary = "Get the bank transactions for the given categories and categorize them based on category description ", 
			description = "Gets the transactions from Fratal API based on given companyId,bankId and accountId. "
					+ "Categorizes data based on given input categories.\r\n"  ,security = @SecurityRequirement(name = "myOauth2Security"))

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successfully got the transactions",content = @Content(schema = @Schema(implementation = CategorizedTransactionsList.class)),links = {
            @Link(name="self"), @Link(name="next"),@Link(name="first")}),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "404", description = "Records Not Found"),
			@ApiResponse(responseCode = "403", description = "Access Denied. Missing authentication token") })
	@GetMapping(value = "/{categoryIds}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategorizedTransactionsList> getCategorizedTransactions(
			@Parameter(description = "Category description to search. Comma separated multiple values are allowed") @PathVariable @NotEmptyFields String[] categoryIds,
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE) @Min(1) Integer page,
			@Parameter(description = "maximum number of records to return") @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) @Min(1) @Max(30) Integer size,
			@Parameter(description = "Parameter required for Fractal API to access transactions ") @RequestParam(value = "bankId", required = true) @Min(1) Integer bankId,
			@Parameter(description = "Parameter required for Fractal API to access transactions ") @RequestParam(value = "companyId", required = false) @Min(1) Integer companyId,
			@Parameter(description = "Parameter required for Fractal API to access transactions ") @RequestParam(value = "accountId", required = true) String accountId) {

		// call the service to get the list of bank transactions based on categories
		CategorySearchInfo categorySearchInfo = new CategorySearchInfo();
		categorySearchInfo.setCategoryIds(Arrays.asList(categoryIds));
		categorySearchInfo.setPageInfo(new PageInfo(page, size));
		categorySearchInfo.setBankId(bankId);
		if(companyId!=null) {
		categorySearchInfo.setCompanyId(companyId);
		}
		categorySearchInfo.setAccountId(accountId);

		CategorizedTransactionsList categorizedTransaction = categoryService
				.getCategorizedTransactions(categorySearchInfo);

		logger.info("Got the categorized transactions for {}, {}", categoryIds, categorizedTransaction);
		if (categorizedTransaction != null && categorizedTransaction.getCategorizedTransactions() != null
				&& !categorizedTransaction.getCategorizedTransactions().isEmpty()) {

			categorizedTransaction = responseAssembler.addLinks(categorizedTransaction, page, size, categoryIds);
			return new ResponseEntity<>(categorizedTransaction, HttpStatus.OK);
		} else {

			throw new TransactionsNotFoundException(categoryIds);

		}

	}

	/**
	 * Updates the transaction category description
	 * @param categorizedtransactions
	 * @param existingCategoryId
	 * @return
	 */

	@Operation(summary = "Updates the transaction category description for all transactions or for given particular transaction", description = " If category needs to updated for all transactions , then provide only categoryId. bankTransaction can be blank . \r\n  Sample Input:  "
			+ "{categoryId\": \"updated category description\\\", \"bankTransaction\\\": [{\\\"transactionId\\\":\\\"8439173b-4ef3-44c4-82fe-282bbae35dba\\\"},{\\\"transactionId\\\":\\\"8e4d2111-859c-49b1-8557-d5f52fdf6685\\\"}]}\r\n. Note : As I am not using any Database , updated transactions can be found only in API response ",security = @SecurityRequirement(name = "myOauth2Security"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successfully updated the transactions" ,content = @Content(schema = @Schema(implementation = BankTransaction.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "404", description = "Records Not Found"),
			@ApiResponse(responseCode = "403", description = "Access Denied. Missing authentication token") })
	@PutMapping(value = "/transactions/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BankTransaction> updateCategory(
			@Parameter(description = "Updated category description and transactions which needs to be updated") @RequestBody @NotNull @Valid CategorisedTransaction categorizedtransactions,
			@Parameter(description = "Category That needs to be updated") @PathVariable(value = "categoryId") @NotBlank String existingCategoryId,
			@Parameter(description = "Parameter required for Fractal API to access transactions. Fractal service bankId ") @RequestParam(value = "bankId", required = true) @Min(1) Integer bankId,
			@Parameter(description = "Parameter required for Fractal API to access transactions.Fractal service companyId  ") @RequestParam(value = "companyId", required = false) @Min(1) Integer companyId,
			@Parameter(description = "Parameter required for Fractal API to access transactions.Fractal service accountId ") @RequestParam(value = "accountId", required = true) String accountId
			) {

		BankTransactionSearchInfo bankTransactionSearchInfo =new BankTransactionSearchInfo();
		bankTransactionSearchInfo.setAccountId(accountId);
		bankTransactionSearchInfo.setBankId(bankId);
		if(companyId!=null) {
		bankTransactionSearchInfo.setCompanyId(companyId);
		}
		
		BankTransaction updatedTransactions = categoryService.updateCategory(categorizedtransactions,
				existingCategoryId,bankTransactionSearchInfo);

		logger.info("updatedTransactions for {}, {}", existingCategoryId, updatedTransactions);
		if (updatedTransactions != null && updatedTransactions.getResults() != null
				&& (!updatedTransactions.getResults().isEmpty())) {

			return new ResponseEntity<>(updatedTransactions, HttpStatus.OK);
		} else {

			throw new CategoryNotFoundException(existingCategoryId);

		}

	}

	/**
	 * Adds a new Category (CatogoryId , Description)
	 * 
	 * @param categorizedtransaction
	 * @return
	 */

	@Operation(summary = "Creates new Category (CategoryId , Category Description) ",  description = " Sample Input:"
			+ "\r\n{\"categoryId\": \"New category Id\",\"categoryDesc\": \"New category description\"}\r\n",security = @SecurityRequirement(name = "myOauth2Security"))
	
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successfully Created Transaction" ,content = @Content(schema = @Schema(implementation = CategorisedTransaction.class)),links = {
            @Link(name="self")}),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "409", description = "Creation of category failed.Category Already Exists"),
			@ApiResponse(responseCode = "403", description = "Access Denied. Missing authentication token") })
	@PostMapping(value = "" ,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategorisedTransaction> createCategory(
			@Parameter(description = "Category which needs to be created. provide categoryId and Category description as input") @RequestBody @Valid CategorisedTransaction categorizedtransaction) {

		CategorisedTransaction newCategorizedtransaction = categoryService.createCategory(categorizedtransaction);

		if (newCategorizedtransaction != null) {

			newCategorizedtransaction = responseAssembler.addLinks(newCategorizedtransaction);
			logger.info("Created the transaction");
			return new ResponseEntity<>(newCategorizedtransaction, HttpStatus.CREATED);
		} else {

			throw new CreateCategoryException(categorizedtransaction.getCategoryId().trim());

		}

	}

}
