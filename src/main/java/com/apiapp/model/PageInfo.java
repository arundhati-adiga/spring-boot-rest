package com.apiapp.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Valid
public class PageInfo {
	
	@Min(value = 1) 
	private int page;
	@Min(value = 1) 
	@Max(value = 30)
	private int size;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public PageInfo(int page, int size) {
		super();
		this.page = page;
		this.size = size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
