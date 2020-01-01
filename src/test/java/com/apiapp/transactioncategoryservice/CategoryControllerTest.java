package com.apiapp.transactioncategoryservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.apiapp.controller.CategoryController;
import com.apiapp.model.BankTransactionSearchInfo;
import com.apiapp.model.CategorisedTransaction;
import com.apiapp.model.CategorySearchInfo;
import com.apiapp.model.JwtRequest;
import com.apiapp.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Data is available at /test/testdata.txt
 * 
 * @author Arundhati Adiga CategoryControllerTest.java
 */

@ContextConfiguration(classes = { CategoryController.class, CategoryService.class })
@WebMvcTest
public class CategoryControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CategoryService categoryService;

	private JacksonTester<CategorisedTransaction> json;

	

	@Test
	public void testGetCategoriesSuccess() throws Exception {

		// String authToken=obtainAccessToken();

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(TestUtil.getCategoryList());

		MockHttpServletResponse result = null;

		result = mvc.perform(get("/categories/Mock category?page=1&size=1&bankId=6&accountId=fakeacc")

				.accept(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn().getResponse();

		// Verify request succeed
		assertEquals(200, result.getStatus());
		assertSame(true, result.getContentAsString().contains("Mock category"));

	}

	@Test
	public void testGetCategoriestestGetCategoriesInvalidPage() throws Exception {

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(TestUtil.getCategoryList());

		// MockHttpServletResponse result=null;

		try {
			MvcResult result = mvc.perform(get("/categories/cat1?page=0&size=1&bankId=6&accountId=fakeacc")

					.accept(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		} catch (Exception ex) {
			assertSame(true, ex.getMessage().contains("page: must be greater than or equal to 1"));

		}

	}

	@Test
	public void testGetCategoriestestGetCategoriesFailureCategoryID() throws Exception {

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(TestUtil.getCategoryList());

		try {
			MvcResult result = null;

			result = mvc.perform(get("/categories/  ?page=1&size=1&bankId=6&accountId=fakeacc")

					.accept(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

			fail();

		} catch (Exception ex) {
			assertSame(true, ex.getMessage().contains("categoryIds: Array cannot contain empty fields"));

		}

		/**
		 * assertEquals(400, result.getResponse().getStatus()); assertSame(true,
		 * result.getResolvedException().getMessage().contains("categoryIds: Array
		 * cannot contain empty fields"));
		 **/

	}

	@Test
	public void testGetCategoriestestGetCategoriesMissingHeaders() throws Exception {

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(TestUtil.getCategoryList());

		MockHttpServletResponse result = null;

		result = mvc.perform(get("/categories/cat1?bankId=6&accountId=fakeacc").accept(MediaType.APPLICATION_JSON)
				.with(user("admin")).with(csrf())).andReturn().getResponse();

		assertEquals(200, result.getStatus());
		// assertSame(true, result.getErrorMessage().contains("Access Denied"));

	}

	@Test
	public void testGetMultipleCategoriesSuccess() throws Exception {

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(TestUtil.getMultipleCategoryList());

		MockHttpServletResponse result = null;

		result = mvc.perform(get("/categories/Mock category,Mock category2?bankId=6&accountId=fakeacc")

				.accept(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn().getResponse();

		// Verify request succeed
		assertEquals(200, result.getStatus());
		assertSame(true, result.getContentAsString().contains("Mock category")
				&& result.getContentAsString().contains("Mock category2"));

	}

	@Test
	public void testGetCategoriesSuccessDefaultPageandSize() throws Exception {

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(TestUtil.getCategoryList());

		MockHttpServletResponse result = null;

		result = mvc.perform(get("/categories/Mock category?bankId=6&accountId=fakeacc")

				.accept(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn().getResponse();

		// Verify request succeed
		assertEquals(200, result.getStatus());
		assertSame(true, result.getContentAsString().contains("Mock category"));

	}

	@Test
	public void testGetCategoriesNoResultFound() throws Exception {

		Mockito.when(categoryService.getCategorizedTransactions(Mockito.any(CategorySearchInfo.class)))
				.thenReturn(null);

		try {
			MvcResult result = null;

			result = mvc.perform(get("/categories/Mock category?bankId=6&accountId=fakeacc")

					.accept(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		} catch (Exception ex) {
			assertSame(true, ex.getMessage().contains("Transactions not found"));

		}

	}

	@Test
	public void testupdateCategoriesSuccess() throws Exception {

		MvcResult result = null;

		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);

		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category", "new category Desc");

		Mockito.when(categoryService.updateCategory(Mockito.any(CategorisedTransaction.class),
				Mockito.any(String.class), Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getupdatedTransactionRespone());

		result = mvc.perform(put("/categories/transactions/Mock category?bankId=6&accountId=fakeacc")
				.content(json.write(categorisedTransaction).getJson()).

				contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		// Verify request succeed
		assertEquals(200, result.getResponse().getStatus());
		assertSame(true, result.getResponse().getContentAsString().contains("new category"));

	}

	@Test
	public void testupdateCategoriesInvidInputInBody() throws Exception {

		MvcResult result = null;

		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);

		CategorisedTransaction categorisedTransaction = new CategorisedTransaction(" ", "desc");

		Mockito.when(categoryService.updateCategory(Mockito.any(CategorisedTransaction.class),
				Mockito.any(String.class), Mockito.any(BankTransactionSearchInfo.class)))
				.thenReturn(TestUtil.getupdatedTransactionRespone());

		result = mvc.perform(
				put("/categories/transactions/Mock category").content(json.write(categorisedTransaction).getJson()).

						contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf()))
				.andReturn();

		// Verify request succeed
		assertEquals(400, result.getResponse().getStatus());
		assertSame(true, result.getResolvedException().getMessage().contains("CategoryId cannot be null or blank"));

	}

	@Test
	public void testupdateCategoriesInvidInputPathVariable() throws Exception {

		try {

			MvcResult result = null;

			ObjectMapper objectMapper = new ObjectMapper();
			JacksonTester.initFields(this, objectMapper);

			CategorisedTransaction categorisedTransaction = new CategorisedTransaction("category id", "desc");

			Mockito.when(categoryService.updateCategory(Mockito.any(CategorisedTransaction.class),
					Mockito.any(String.class), Mockito.any(BankTransactionSearchInfo.class)))
					.thenReturn(TestUtil.getupdatedTransactionRespone());

			result = mvc.perform(put("/categories/transactions/ ?bankId=6&accountId=fakeacc")
					.content(json.write(categorisedTransaction).getJson()).

					contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		} catch (Exception ex) {
			assertSame(true, ex.getMessage().contains("updateCategory.existingCategoryId: must not be blank"));

		}

	}

	@Test
	public void testupdateCategoriesNoRecordsFound() throws Exception {

		try {

			MvcResult result = null;

			ObjectMapper objectMapper = new ObjectMapper();
			JacksonTester.initFields(this, objectMapper);

			CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new Category", "desc");

			Mockito.when(categoryService.updateCategory(Mockito.any(CategorisedTransaction.class),
					Mockito.any(String.class), Mockito.any(BankTransactionSearchInfo.class))).thenReturn(null);

			result = mvc.perform(put("/categories/transactions/Mock category?bankId=6&accountId=fakeacc")
					.content(json.write(categorisedTransaction).getJson()).

					contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		} catch (Exception ex) {
			assertSame(true,
					ex.getMessage().contains("Invalid category or transactions were not found for given categories"));

		}

	}

	@Test
	public void testaddCategoriesSuccess() throws Exception {

		MvcResult result = null;

		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);

		CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category", "new category Desc");

		Mockito.when(categoryService.createCategory(Mockito.any(CategorisedTransaction.class)))
				.thenReturn(categorisedTransaction);

		result = mvc.perform(post("/categories/").content(json.write(categorisedTransaction).getJson()).

				contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		// Verify request succeed
		assertEquals(201, result.getResponse().getStatus());
		assertSame(true, result.getResponse().getContentAsString().contains("new category"));

	}

	@Test
	public void testaddCategoriesFailAlreadyExits() throws Exception {

		try {

			MvcResult result = null;

			ObjectMapper objectMapper = new ObjectMapper();
			JacksonTester.initFields(this, objectMapper);

			CategorisedTransaction categorisedTransaction = new CategorisedTransaction("new category",
					"new category Desc");

			Mockito.when(categoryService.createCategory(Mockito.any(CategorisedTransaction.class))).thenReturn(null);

			result = mvc.perform(post("/categories/").content(json.write(categorisedTransaction).getJson()).

					contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		} catch (Exception ex) {
			assertSame(true, ex.getMessage().contains("Category Already Exists"));

		}

	}

	@Test
	public void testaddCategoriesFailInvalidInput() throws Exception {

		MvcResult result = null;

		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);

		CategorisedTransaction categorisedTransaction = new CategorisedTransaction(" ", "new category Desc");

		Mockito.when(categoryService.createCategory(Mockito.any(CategorisedTransaction.class)))
				.thenReturn(categorisedTransaction);

		result = mvc.perform(post("/categories/").content(json.write(categorisedTransaction).getJson()).

				contentType(MediaType.APPLICATION_JSON).with(user("admin")).with(csrf())).andReturn();

		// Verify request succeed
		assertEquals(400, result.getResponse().getStatus());
		assertSame(true, result.getResolvedException().getMessage().contains("CategoryId cannot be null or blank"));

	}

	/**
	 * private String obtainAccessToken() throws Exception {
	 * 
	 * JwtRequest jwtRequest=new JwtRequest("admin","password");
	 * 
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper();
	 * JacksonTester.initFields(this, objectMapper);
	 * 
	 * 
	 * MvcResult result =
	 * mvc.perform(post("/authenticate").content(jsonRequest.write(jwtRequest).getJson()).
	 * contentType(MediaType.APPLICATION_JSON)).andReturn();
	 * 
	 * String resultString = result.getResponse().toString();
	 * 
	 * JacksonJsonParser jsonParser = new JacksonJsonParser(); return
	 * jsonParser.parseMap(resultString).get("access_token").toString(); }
	 **/

}
