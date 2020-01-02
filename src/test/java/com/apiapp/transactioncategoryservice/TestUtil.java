package com.apiapp.transactioncategoryservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ResourceUtils;

import com.apiapp.model.BankTransaction;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.CategorizedTransactionsList;
import com.apiapp.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

	public static HttpEntity<String> setHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("api-auth-token", "apitokenvalue");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return entity;
	}
	
	

	public static HttpHeaders getHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("api-auth-token", "apitokenvalue");
		
		return headers;
	}
	
	
	public static List<Transaction> getMockBankTransactions() {
		
		String myJson;
		List<Transaction> bankTransactions=new ArrayList<>();
		try {
			
			
			
			File file = ResourceUtils.getFile("classpath:test"+File.separator+"testdata.txt");
				
			 myJson = new String(Files.readAllBytes(file.toPath()));

			//myJson = new Scanner(new File("C:\\springwork\\transaction-category-service\\src\\test\\java\\com\\apiapp\\transactioncategoryservice\\testdata.txt")).next();
			JSONObject myJsonobject = new JSONObject(myJson);
			JSONArray arr = myJsonobject.getJSONArray("results");
			ObjectMapper mapper = new ObjectMapper();
			BankTransaction bankTransactionsList = mapper.readValue(myJson, BankTransaction.class);
			bankTransactions=bankTransactionsList.getResults();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		/*
		 * List<Transaction> bankTransactions=new ArrayList<>(); ObjectMapper mapper =
		 * new ObjectMapper(); TypeReference<BankTransaction> typeReference = new
		 * TypeReference<BankTransaction>(){}; InputStream inputStream =
		 * TypeReference.class.getResourceAsStream(
		 * "C:\\springwork\\transaction-category-service\\src\\test\\java\\com\\apiapp\\transactioncategoryservice\\testdata.txt"
		 * );
		 * 
		 * try { bankTransactions = (List<Transaction>)
		 * mapper.readValue(inputStream,typeReference);
		 * 
		 * } catch (IOException e){ e.printStackTrace(); }
		 */
		return bankTransactions;
	}
	
	
	
	
	public static CategorizedTransactionsList getCategoryList(){
		
			
		CategorizedTransactionsList categorizedTransactionsList=new CategorizedTransactionsList(); 
		 CategorisedTransaction categorizedTransactions=new CategorisedTransaction("Mock category",getMockBankTransactions()); 
		  List<CategorisedTransaction> categorisedTransactionArray=new ArrayList<>();
		  categorisedTransactionArray.add(categorizedTransactions);
		  categorizedTransactionsList.setCategorizedTransactions(categorisedTransactionArray);
		return categorizedTransactionsList;
		
	}
	
	
	
	public static CategorizedTransactionsList getMultipleCategoryList(){
		
		
		CategorizedTransactionsList categorizedTransactionsList=new CategorizedTransactionsList(); 
		 CategorisedTransaction categorizedTransactions=new CategorisedTransaction("Mock category",getMockBankTransactions()); 
		  List<CategorisedTransaction> categorisedTransactionArray=new ArrayList<>();
		  categorisedTransactionArray.add(categorizedTransactions);
		  
		 
			 CategorisedTransaction categorizedTransactions2=new CategorisedTransaction("Mock category2",getMockBankTransactions()); 
			 
			  categorisedTransactionArray.add(categorizedTransactions2);
		  
		  categorizedTransactionsList.setCategorizedTransactions(categorisedTransactionArray);
		return categorizedTransactionsList;
		
	}
	
	
	public static String toJson(CategorisedTransaction object) throws IOException {
		
	
        ObjectMapper mapper = new ObjectMapper();
        
       return mapper.writeValueAsString(object);
       /** mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object); **/
    }
	
	
public static BankTransaction getupdatedTransactionRespone(){
		
		
	Transaction transaction=getMockBankTransactions().get(0);
	transaction.setDescription("new category");
	
	List<Transaction> transactionList=new ArrayList<>();
	transactionList.add(transaction);
	
	BankTransaction bankTransaction=new BankTransaction();
	bankTransaction.setResults(transactionList);
	
		
		return bankTransaction;
		
	}
	
	

}
