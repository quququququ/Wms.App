package com.wms.newwmsapp.model;

import java.io.Serializable;

public class PageFilterModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8626406063152462911L;
	public String CurrentIndex;
	public String PageSize;
	public String getCurrentIndex() {
		return CurrentIndex;
	}
	public void setCurrentIndex(String currentIndex) {
		CurrentIndex = currentIndex;
	}
	public String getPageSize() {
		return PageSize;
	}
	public void setPageSize(String pageSize) {
		PageSize = pageSize;
	}
}
