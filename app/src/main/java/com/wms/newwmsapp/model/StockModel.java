package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

public class StockModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683093673917481178L;
	private String StockCode;
	private String StockName;
	private List<CustGoodModel> CustGoods;
	public String getStockCode() {
		return StockCode;
	}
	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}
	public String getStockName() {
		return StockName;
	}
	public void setStockName(String stockName) {
		StockName = stockName;
	}
	public List<CustGoodModel> getCustGoods() {
		return CustGoods;
	}
	public void setCustGoods(List<CustGoodModel> custGoods) {
		CustGoods = custGoods;
	}
	
}
