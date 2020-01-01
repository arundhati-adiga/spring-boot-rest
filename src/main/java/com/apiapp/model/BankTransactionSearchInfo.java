package com.apiapp.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Valid
public class BankTransactionSearchInfo {
	
	
	@NotNull
	private String accountId;
	@NotNull
	private int bankId;
	@NotNull
	private int companyId;;
	private Date fromDate;
	private Date toDate;
	private int page;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	
	

}
