package com.apiapp.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

@Valid
public class CategorisedTransaction extends RepresentationModel{
	
	
	public CategorisedTransaction() {
		
	}
	
	public CategorisedTransaction(@NotBlank(message = "CategoryId cannot be null or blank")  String categoryId, List<Transaction> bankTransaction) {
		super();
		this.categoryId = categoryId;
		this.categoryDesc = categoryId;
		this.bankTransaction = bankTransaction;
	}

	

	public CategorisedTransaction(@NotBlank(message = "CategoryId cannot be null or blank") String categoryId,
			String categoryDesc) {
		super();
		this.categoryId = categoryId;
		this.categoryDesc = categoryDesc;
	}



	@NotBlank(message="CategoryId cannot be null or blank")
	private String categoryId;
	
	private String categoryDesc;
	
	@Valid
	private List<Transaction> bankTransaction;
	
	private int version;
	

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public List<Transaction> getBankTransaction() {
		return bankTransaction;
	}

	@Override
	public String toString() {
		return "CategorisedTransaction [categoryId=" + categoryId + ", bankTransaction=" + bankTransaction + "]";
	}

	public void setBankTransaction(List<Transaction> bankTransaction) {
		this.bankTransaction = bankTransaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bankTransaction == null) ? 0 : bankTransaction.hashCode());
		result = prime * result + ((categoryDesc == null) ? 0 : categoryDesc.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + version;
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
		CategorisedTransaction other = (CategorisedTransaction) obj;
		if (bankTransaction == null) {
			if (other.bankTransaction != null)
				return false;
		} else if (!bankTransaction.equals(other.bankTransaction))
			return false;
		if (categoryDesc == null) {
			if (other.categoryDesc != null)
				return false;
		} else if (!categoryDesc.equals(other.categoryDesc))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	

	
	
	

}
