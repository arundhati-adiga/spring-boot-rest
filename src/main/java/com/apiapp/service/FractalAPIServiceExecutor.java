/**
 * 
 */
package com.apiapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiapp.exception.FractalServiceException;
import com.apiapp.model.BankTransaction;
import com.apiapp.model.BankTransactionSearchInfo;
import com.apiapp.model.Transaction;

/**
 * This class is responsible to execute Fractal sandbox API's to get the bank
 * transactions
 * 
 * @author Arundhati Adiga FractalAPIServiceExecutor.java
 */
@Validated
@Service
public class FractalAPIServiceExecutor {

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(FractalAPIServiceExecutor.class);
	
	

	/**
	 * Get the list of transaction for a given company , bank and account
	 * 
	 * @param bankTransactionSearchInfo
	 * @return
	 */

	public List<Transaction> getBankTransactionsForCompany(@Valid BankTransactionSearchInfo bankTransactionSearchInfo) {

		
		List<Transaction> bankTransactions;

		// URI (URL) parameters
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("bankId",String.valueOf(bankTransactionSearchInfo.getBankId()));//6

		urlParams.put("accountId", bankTransactionSearchInfo.getAccountId());//fakeacc

		// Query parameters
		MultiValueMap<String, String> params=new LinkedMultiValueMap<>();
		params.add("pg",env.getProperty("fractal.api.pg"));
		if(bankTransactionSearchInfo.getCompanyId()>0) {
			params.add("companyId", String.valueOf(bankTransactionSearchInfo.getCompanyId()));//2
			}
		
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(env.getProperty("fractal.api.url")).queryParams(params)
				// Add query parameter
				
				.queryParam("pg", env.getProperty("fractal.api.pg"));//1

		final HttpHeaders headers = new HttpHeaders();

		headers.set("X-Api-Key", env.getProperty("fractal.api.X-Api-Key"));
		headers.set("Authorization", env.getProperty("fractal.api.Authorization"));
		headers.set("X-Partner-Id", env.getProperty("fractal.api.X-Partner-Id"));

		// restTemplate.

		// Create a new HttpEntity
		final HttpEntity<String> entity = new HttpEntity<>(headers);

		
		logger.debug("URI={}",builder.buildAndExpand(urlParams).toUri());
		ResponseEntity<BankTransaction> responsebankTransactions = restTemplate
				.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, entity, BankTransaction.class);

	
		
		if ((!responsebankTransactions.getStatusCode().isError()) && responsebankTransactions.getBody() != null && responsebankTransactions.getBody().getResults() != null) {

			bankTransactions = responsebankTransactions.getBody().getResults();
		}else {
			
			throw new FractalServiceException(responsebankTransactions.getStatusCode().toString());
			
		}

		logger.debug("bankTransactions Results from API {} ",bankTransactions );
		return bankTransactions;

	}

}
