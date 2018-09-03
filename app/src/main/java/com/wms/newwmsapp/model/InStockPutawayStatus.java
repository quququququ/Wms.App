package com.wms.newwmsapp.model;

import java.io.Serializable;

public class InStockPutawayStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8465330100959420204L;
	private String InStockPutawayStatusCode;
	private String InStockPutawayStatusName;
	public String getInStockPutawayStatusCode() {
		return InStockPutawayStatusCode;
	}
	public void setInStockPutawayStatusCode(String inStockPutawayStatusCode) {
		InStockPutawayStatusCode = inStockPutawayStatusCode;
	}
	public String getInStockPutawayStatusName() {
		return InStockPutawayStatusName;
	}
	public void setInStockPutawayStatusName(String inStockPutawayStatusName) {
		InStockPutawayStatusName = inStockPutawayStatusName;
	}
}
